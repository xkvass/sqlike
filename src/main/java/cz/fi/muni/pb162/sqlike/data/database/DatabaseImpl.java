package cz.fi.muni.pb162.sqlike.data.database;

import cz.fi.muni.pb162.sqlike.data.table.Table;
import cz.fi.muni.pb162.sqlike.data.table.TableImpl;
import cz.fi.muni.pb162.sqlike.data.table.row.Row;
import cz.fi.muni.pb162.sqlike.data.table.row.RowImpl;
import cz.fi.muni.pb162.sqlike.data.table.value.Value;
import cz.fi.muni.pb162.sqlike.data.table.value.ValueImpl;
import cz.fi.muni.pb162.sqlike.data.type.DataTypeImpl;
import cz.fi.muni.pb162.sqlike.query.Query;
import cz.fi.muni.pb162.sqlike.query.QueryAlterTable;
import cz.fi.muni.pb162.sqlike.query.QueryCreateTable;
import cz.fi.muni.pb162.sqlike.query.QueryDelete;
import cz.fi.muni.pb162.sqlike.query.QueryDropTable;
import cz.fi.muni.pb162.sqlike.query.QueryInsert;
import cz.fi.muni.pb162.sqlike.query.QuerySelect;
import cz.fi.muni.pb162.sqlike.query.QueryType;
import cz.fi.muni.pb162.sqlike.query.QueryUpdate;
import cz.fi.muni.pb162.sqlike.query.builder.field.Field;
import cz.fi.muni.pb162.sqlike.query.builder.field.FieldClass;
import cz.fi.muni.pb162.sqlike.query.builder.field.FieldImpl;
import cz.fi.muni.pb162.sqlike.query.builder.field.OrderedField;
import cz.fi.muni.pb162.sqlike.query.result.QueryResult;
import cz.fi.muni.pb162.sqlike.query.result.QueryResultImpl;
import cz.fi.muni.pb162.sqlike.query.result.ResultRow;
import cz.fi.muni.pb162.sqlike.query.result.ResultRowImpl;
import cz.fi.muni.pb162.sqlike.query.result.Status;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static cz.fi.muni.pb162.sqlike.data.type.DataTypeImpl.INTEGER;
import static cz.fi.muni.pb162.sqlike.data.type.DataTypeImpl.REAL;
import static cz.fi.muni.pb162.sqlike.query.QueryTypeImpl.*;
import static cz.fi.muni.pb162.sqlike.query.builder.field.FieldType.*;
import static cz.fi.muni.pb162.sqlike.query.result.QueryResultStatus.OK;
import static cz.fi.muni.pb162.sqlike.query.result.QueryResultStatus.ERROR;

/**
 * A class that implements the Database interface.
 *
 * @author Valeria Kvassayova
 */
public class DatabaseImpl implements Database {
    private final String dbName;
    private final List<Table> tables = new ArrayList<>();

    //these attributes hold information for QueryResult creation
    private Status resultStatus = OK;
    private Optional<Iterable<ResultRow>> payload;
    private int rowsAffected = 0;

    /**
     * Constructor creates a database with a name given.
     * Upon creation, the database is empty.
     *
     * @param dbName name of the database
     */
    public DatabaseImpl(String dbName) {
        this.dbName = dbName;
    }

    public List<Table> getTables() {
        return tables;
    }

    @Override
    public QueryResult execute(Query query) {
        resultStatus = OK;
        payload = Optional.empty();
        if (query == null) {
            throw new IllegalArgumentException("Incorrect use of QueryBuilder.");
        }
        QueryType queryType = query.getQueryType();
        switch (queryType) {
            case ALTER_TABLE_ADD_COLUMN,
                 ALTER_TABLE_DROP_COLUMN,
                 ALTER_TABLE_RENAME_TABLE:
                QueryAlterTable aQuery = (QueryAlterTable) query;
                execAlterTable(aQuery);
                break;
            case CREATE_TABLE:
                QueryCreateTable ctQuery = (QueryCreateTable) query;
                execCreateTable(ctQuery);
                break;
            case INSERT:
                QueryInsert iQuery = (QueryInsert) query;
                execInsert(iQuery);
                break;
            case DELETE:
                QueryDelete dQuery = (QueryDelete) query;
                execDelete(dQuery);
                break;
            case DROP_TABLE:
                QueryDropTable dtQuery = (QueryDropTable) query;
                execDropTable(dtQuery);
                break;
            case SELECT:
                QuerySelect sQuery = (QuerySelect) query;
                execSelect(sQuery);
                break;
            case UPDATE:
                QueryUpdate uQuery = (QueryUpdate) query;
                execUpdate(uQuery);
                break;
            default:
                throw new UnsupportedOperationException("Unknown QueryType.");
        }
        return new QueryResultImpl(resultStatus, payload, rowsAffected);
    }

    /**
     * Private method called by method execute(). Executes the given query.
     * Alters table according the query.
     *
     * @param query query to be executed
     */
    private void execAlterTable(QueryAlterTable query) {
        TableImpl table = findTable(query.getTableName());
        if (table == null) {
            resultStatus = ERROR;
            return;
        }
        if (query.getNewTableName() != null) {
            table.setName(query.getNewTableName());
            return;
        }
        if (query.getAddColumn() != null) {
            table.addColumn(query.getAddColumn(), query.getColumnType());
            for (RowImpl row : table.getRows()) {
                row.getRow().put(query.getAddColumn(), new ValueImpl(query.getColumnType(), query.getAddColumn(), null));
            }
        }
        if (query.getDropColumn() != null) {
            table.dropColumn(query.getDropColumn());
            for (RowImpl row : table.getRows()) {
                row.getRow().remove(query.getDropColumn());
            }
        }
        rowsAffected = table.getRows().size();
    }

    /**
     * Private method called by method execute(). Executes the given query.
     * Creates table and ties it with a database.
     *
     * @param query query to be executed
     */
    private void execCreateTable(QueryCreateTable query) {
        if (!query.getHeader().containsKey(query.getPrimaryKey())) {
            resultStatus = ERROR;
            return;
        }
        Table table = new TableImpl(query.getTableName(), query.getHeader(), query.getPrimaryKey());
        if (getTables().contains(table)) {
            resultStatus = ERROR;
            return;
        }
        getTables().add(table);
    }

    /**
     * Private method called by method execute(). Executes the given query.
     * Inserts given values into database.
     *
     * @param query query to be executed
     */
    private void execInsert(QueryInsert query) {
        TableImpl table = findTable(query.getTableName());
        if (table == null) {
            resultStatus = ERROR;
            return;
        }
        rowsAffected = table.insert(query.getRows().toArray(new Row[0]));
        if (rowsAffected != query.getRows().size()) {
            resultStatus = ERROR;
        }
    }

    /**
     * Private method called by method execute(). Executes the given query.
     * Deletes rows from table.
     *
     * @param query query to be executed
     */
    private void execDelete(QueryDelete query) {
        TableImpl table = findTable(query.getTableName());
        if (table == null) {
            resultStatus = ERROR;
            return;
        }
        rowsAffected = table.delete(query.getWhere());
    }


    /**
     * Private method called by method execute(). Executes the given query.
     * Removes table from database.
     *
     * @param query query to be executed
     */
    private void execDropTable(QueryDropTable query) {
        if (!getTables().removeIf(table -> table.getName().equals(query.getTableName()))) {
            resultStatus = ERROR;
        }
    }

    /**
     * Private method called by method execute(). Executes the given query.
     * Selects attributes from table according to the query.
     *
     * @param query query to be executed
     */
    private void execSelect(QuerySelect query) {
        // from clause
        TableImpl table = findTable(query.getTableName());
        if (table == null) {
            resultStatus = ERROR;
            return;
        }
        for (String column : query.getColumnNames()) {
            if (!Arrays.asList(table.getColumnNames()).contains(column)) {
                resultStatus = ERROR;
                return;
            }
        }

        List<RowImpl> rows = new ArrayList<>(table.getRows());
        // where clause
        if (query.getWhere() != null) {
            rows = rows.stream().filter(r -> query.getWhere().apply(r)).collect(Collectors.toList());
        }

        // groupBy + having clause
        if (query.getGroupBy() != null) {
            rows = new ArrayList<>(groupBy(query, rows));

            if (query.getHaving() != null) {
                rows = rows.stream()
                        .filter(r -> query.getHaving().apply(r))
                        .collect(Collectors.toList());
            }
        }

        //orderBy clause
        if (query.getOrderBy() != null) {
            rows.sort(orderBy(query));
        }

        // select clause
        Field[] columns = Arrays.copyOf(query.getColumns(), query.getColumns().length);
        if (columns.length == 0) {
            columns = Arrays.stream(table.getColumnNames())
                    .map(col -> new FieldImpl(col, COLUMN))
                    .toArray(Field[]::new);
        }
        payload = Optional.of(new ArrayList<>());
        for (RowImpl row : rows) {
            Map<Field, Value> values = new LinkedHashMap<>();
            for (Field f : columns) {
                String name = f.getFieldName();
                if (f.getFieldClass().isAggregate()) {
                    name = f.getFieldClass().getName() + "(" + name + ")";
                }
                Value value = row.getValue(name);
                if (f.getAlias() != null) {
                    name = f.getAlias();
                }
                values.put(f, new ValueImpl(value.getType(), name, value.getValue()));
            }
            ((List<ResultRow>) payload.get()).add(new ResultRowImpl(table, values));
        }

        //offset, limit
        payload = Optional.of(((List<ResultRow>) payload.get()).stream().skip(query.getOffset()).limit(query.getLimit()).toList());
        rowsAffected = ((List<ResultRow>) payload.get()).size();

        //exportToCsv
        if (query.getFileName() != null) {
            try (OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream(query.getFileName(), false),
                    query.getCharsetName())) {
                if (query.isHeader()) {
                    StringBuilder builder = new StringBuilder();
                    for (Field f : columns) {
                        builder.append(f.toSql());
                        builder.append(query.getDelimiter());
                    }
                    builder.deleteCharAt(builder.length() - 1);
                    writer.write(builder.toString());
                    writer.write("\n");
                }
                Iterator<ResultRow> iter = payload.get().iterator();
                while (iter.hasNext()) {
                    ResultRow row = iter.next();
                    writer.write(row.toString(query.getDelimiter()));
                    if (iter.hasNext()) {
                        writer.write("\n");
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException("File does not exist.", e);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Invalid encoding.", e);
            } catch (IOException e) {
                throw new RuntimeException("Error while trying to create the file.", e);
            }
        }
    }

    /**
     * Private method called by method execute(). Executes the given query.
     * Updates certain rows in the table.
     *
     * @param query query to be executed
     */
    private void execUpdate(QueryUpdate query) {
        TableImpl table = findTable(query.getTableName());
        if (table == null) {
            resultStatus = ERROR;
            return;
        }
        Map<String, Object> updates = new LinkedHashMap<>();
        for (Field field : query.getUpdates().keySet()) {
            updates.put(field.getFieldName(), query.getUpdates().get(field));
        }
        payload = Optional.empty();
        rowsAffected = table.update(updates, query.getWhere());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Database db) {
            return db.getName().equals(dbName);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return dbName.hashCode();
    }

    @Override
    public String getName() {
        return dbName;
    }

    /**
     * Finds a table by its name.
     *
     * @param tableName name of the table to find
     * @return the table if found, otherwise null
     */
    private TableImpl findTable(String tableName) {
        return (TableImpl) getTables().stream()
                .filter(table -> table.getName().equals(tableName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Private method used by execSelect to execute the GROUP BY clause.
     *
     * @param query query
     * @param rows to be grouped
     * @return grouped rows
     */
    private List<RowImpl> groupBy(QuerySelect query, List<RowImpl> rows) {
        Map<List<Value>, List<RowImpl>> groups = new LinkedHashMap<>();
        for (RowImpl row : rows) {
            List<Value> key = Arrays.stream(query.getGroupBy())
                    .map(field -> row.getValue(field.getFieldName()))
                    .toList();
            groups.putIfAbsent(key, new ArrayList<>());
            groups.get(key).add(row);
        }

        List<RowImpl> groupedRows = new ArrayList<>();
        for (Map.Entry<List<Value>, List<RowImpl>> entry : groups.entrySet()) {
            RowImpl newRow = new RowImpl();
            for (int i = 0; i < query.getGroupBy().length; i++) {
                String name = query.getGroupBy()[i].getFieldName();
                newRow.getRow().put(name, entry.getKey().get(i));
            }
            Set<Field> cols = Arrays.stream(query.getColumns()).collect(Collectors.toSet());
            if (query.getHaving() != null) {
                cols.addAll(query.getHaving().getAssociatedFields());
            }
            for (Field f : cols) {
                if (f.getFieldClass().isAggregate()) {
                    createAggregates(newRow, f, entry.getValue());
                }
            }
            groupedRows.add(newRow);
        }
        return groupedRows;
    }

    /**
     * Private method used by execSelect to create aggregate fields for ResultRow.
     *
     * @param newRow row to store the new information
     * @param f aggregate field
     * @param rows rows to be aggregated
     */
    private void createAggregates(RowImpl newRow, Field f, List<RowImpl> rows) {
        FieldClass type = f.getFieldClass();
        String name = f.getFieldName();
        double sum = 0;
        int count = rows.size();
        Value min = rows.stream()
                .map(row -> row.getValue(f.getFieldName()))
                .filter(Objects::nonNull)
                .min(Comparator.naturalOrder())
                .orElse(null);
        Value max = rows.stream()
                .map(row -> row.getValue(f.getFieldName()))
                .filter(Objects::nonNull)
                .max(Comparator.naturalOrder())
                .orElse(null);
        switch (type) {
            case SUM:
                if (rows.getFirst().getValue(name).getType() == DataTypeImpl.TEXT) {
                    throw new IllegalArgumentException("Invalid type for SUM");
                }
                for (RowImpl row : rows) {
                    Value value = row.getValue(name);
                    if (value != null) {
                        if (value.getType() == DataTypeImpl.INTEGER) {
                            sum += (Integer) value.getValue();
                        } else if (value.getType() == DataTypeImpl.REAL) {
                            sum += (Double) value.getValue();
                        }
                    }
                }
                newRow.getRow().put("SUM("+name+")", new ValueImpl(REAL, name, sum));
                break;
            case COUNT:
                newRow.getRow().put("COUNT("+name+")", new ValueImpl(INTEGER, name, count));
                break;
            case AVG:
                if (rows.getFirst().getValue(name).getType() == DataTypeImpl.TEXT) {
                    throw new IllegalArgumentException("Invalid type for SUM");
                }
                for (RowImpl row : rows) {
                    Value value = row.getValue(name);
                    if (value != null) {
                        if (value.getType() == DataTypeImpl.INTEGER) {
                            sum += (Integer) value.getValue();
                        } else if (value.getType() == DataTypeImpl.REAL) {
                            sum += (Double) value.getValue();
                        }
                    }
                }
                newRow.getRow().put("AVG("+name+")", new ValueImpl(REAL, name, sum/count));
                break;
            case MIN:
                newRow.getRow().put("MIN("+name+")", min);
                break;
            case MAX:
                newRow.getRow().put("MAX("+name+")", max);
                break;
            default:
                throw new IllegalStateException("Unexpected type.");
        }
    }

    /**
     * Private method used by execSelect to execute the ORDER BY clause.
     * It creates a comparator to order the rows in query by.
     *
     * @param query query
     * @return comparator
     */
    private Comparator<RowImpl> orderBy(QuerySelect query) {
        Comparator<RowImpl> comp = null;
        for (OrderedField ord : query.getOrderBy()) {
            Comparator<RowImpl> comparator = (row1, row2) -> {
                Value v1 = row1.getValue(ord.getFieldName());
                Value v2 = row2.getValue(ord.getFieldName());

                if (v1 == null && v2 == null) return 0;
                if (v1 == null) return -1;
                if (v2 == null) return 1;
                return v1.compareTo(v2);
            };
            if (!ord.isAscending().orElse(true)) {
                comparator = comparator.reversed();
            }
            if (comp == null) {
                comp = comparator;
            } else {
                comp = comp.thenComparing(comparator);
            }
        }
        return comp;
    }
}
