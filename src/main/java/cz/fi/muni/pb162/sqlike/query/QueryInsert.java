package cz.fi.muni.pb162.sqlike.query;

import cz.fi.muni.pb162.sqlike.data.table.row.RowImpl;
import cz.fi.muni.pb162.sqlike.data.table.value.Value;
import cz.fi.muni.pb162.sqlike.query.builder.field.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cz.fi.muni.pb162.sqlike.query.QueryTypeImpl.INSERT;

/**
 * This class implements interface Query for SQL insert into table query.
 *
 * @author Lubomir Vargovcik
 * @author Valeria Kvassayova
 */
public class QueryInsert extends QueryImpl {
    private final String tableName;
    private final String[] columnNames;
    private final List<RowImpl> rows;

    /**
     * Constructor.
     *
     * @param tableName Name of the table.
     * @param columnNames Names of added columns.
     * @param rows New values for columns.
     */
    public QueryInsert(String tableName, String[] columnNames, List<RowImpl> rows) {
        this.tableName = tableName;
        this.columnNames = columnNames;
        this.rows = rows;
    }

    public String getTableName() {
        return tableName;
    }

    public String[] getColumnNames() {
        return Arrays.copyOf(columnNames, columnNames.length);
    }

    public List<RowImpl> getRows() {
        return deepCopyRowImplList(rows);
    }

    @Override
    public String toSqlString() {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ");
        builder.append(tableName);
        if (columnNames.length > 0) {
            builder.append(" (");
            builder.append(String.join(", ", columnNames));
            builder.append(")");
        }
        builder.append(" VALUES");
        for (RowImpl row: rows) {
            builder.append(" (");
            for (Value value: row.getValues()) {
                if (value.getType().getSignature().equals("TEXT")) {
                    builder.append("'");
                }
                builder.append(value.getValue().toString());
                if (value.getType().getSignature().equals("TEXT")) {
                    builder.append("'");
                }
                builder.append(", ");
            }
            builder.delete(builder.length() - 2, builder.length());
            builder.append("),");
        }
        builder.delete(builder.length() - 2, builder.length());
        builder.append(");");
        return builder.toString();
    }

    @Override
    public QueryType getQueryType() {
        return INSERT;
    }

    private static List<RowImpl> deepCopyRowImplList(List<RowImpl> original) {
        List<RowImpl> copy = new ArrayList<>();
        for (RowImpl row : original) {
            Map<String, Value> newMap = new HashMap<>();
            for (Map.Entry<String, Value> entry : row.getRow().entrySet()) {
                String columnName = entry.getKey();
                Value value = entry.getValue();
                newMap.put(columnName, value);
            }
            RowImpl newRow = new RowImpl(newMap);
            copy.add(newRow);
        }
        return copy;
    }
}
