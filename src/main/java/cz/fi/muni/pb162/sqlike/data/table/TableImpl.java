package cz.fi.muni.pb162.sqlike.data.table;

import cz.fi.muni.pb162.sqlike.data.table.row.Row;
import cz.fi.muni.pb162.sqlike.data.table.row.RowImpl;
import cz.fi.muni.pb162.sqlike.data.table.value.Value;
import cz.fi.muni.pb162.sqlike.data.table.value.ValueImpl;
import cz.fi.muni.pb162.sqlike.data.type.DataType;
import cz.fi.muni.pb162.sqlike.query.builder.field.Condition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Class implementing the Table interface.
 * This class represents table in database.
 *
 * @author Lubomir Vargovcik
 */
public class TableImpl implements Table {
    private String name;
    private final Map<String, DataType> header;
    private final List<RowImpl> rows;
    private final String primaryKeyColumn;

    /** Constructor.
     *
     * @param name Name of the table.
     * @param header Header defining name and DataTypes of columns.
     * @param primaryKeyColumn Name of the column which serves as primary key.
     */
    public TableImpl(String name, Map<String, DataType> header, String primaryKeyColumn) {
        this.name = name;
        this.header = new LinkedHashMap<>(header);
        this.primaryKeyColumn = primaryKeyColumn;
        this.rows = new ArrayList<>();
    }

    @Override
    public int insert(Row[] rows) {
        int inserted = 0;  // Counter of correctly inserted rows
        for (Row row : rows) {
            RowImpl rowImpl = (RowImpl) row;
            boolean allPresent = true;
            for (Map.Entry<String, DataType> entry : rowImpl.getHeader().entrySet()) {
                if (!header.containsKey(entry.getKey())) {
                    allPresent = false;
                    break;
                }
            }
            if (allPresent) {
                for (Map.Entry<String, DataType> entry : header.entrySet()) {
                    if (!rowImpl.getHeader().containsKey(entry.getKey())) {
                        rowImpl.getRow().put(entry.getKey(), new ValueImpl(entry.getValue(), entry.getKey(), null));
                    }
                }
                for (RowImpl check : this.rows) {
                    Value checkValue = check.getValue(primaryKeyColumn);
                    Value rowValue = rowImpl.getValue(primaryKeyColumn);
                    if (checkValue.equals(rowValue)) {
                        return 0;
                    }
                }
                this.rows.add(rowImpl);
                inserted++;
            }
        }
        return inserted;
    }

    @Override
    public Row[] select(String... columns) {
        List<RowImpl> result = new ArrayList<>();
        for (RowImpl originalRow : rows) {
            Map<String, Value> newValues = new LinkedHashMap<>();
            for (String column : columns) {
                if (originalRow.getHeader().get(column) != null) {
                    newValues.put(column, originalRow.getValue(column));
                }
            }
            result.add(new RowImpl(newValues));
        }
        return result.toArray(new RowImpl[0]);
    }

    @Override
    public int delete(Condition condition) {
        int initialSize = rows.size();
        rows.removeIf(row -> condition == null || condition.apply(row));
        return initialSize - rows.size();
    }

    @Override
    public int update(Map<String, Object> updates, Condition condition) {
        int updatedRows = 0;
        for (RowImpl row : rows) {
            if (condition == null || condition.apply(row)) {
                for (Map.Entry<String, Object> entry : updates.entrySet()) {
                    String column = entry.getKey();
                    Object newValue = entry.getValue();
                    Value oldValue = row.getValue(column);
                    if (oldValue != null) {
                        row.setValue(column, new ValueImpl(oldValue.getType(), column, newValue));
                    }
                }
                updatedRows++;
            }
        }
        return updatedRows;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public Map<String, DataType> getHeader() {
        return Collections.unmodifiableMap(header);
    }

    /**
     * Adds column to table header.
     *
     * @param columnName name of the column to add
     * @param columnType type of the column
     */
    public void addColumn(String columnName, DataType columnType) {
        header.put(columnName, columnType);
    }

    /**
     * Removed column from header.
     *
     * @param columnName name of the column to remove
     */
    public void dropColumn(String columnName) {
        header.remove(columnName);
    }

    public List<RowImpl> getRows() {
        return new ArrayList<>(rows);
    }

    @Override
    public String getPrimaryKeyColumnName() {
        return primaryKeyColumn;
    }

    @Override
    public String[] getColumnNames() {
        return header.keySet().toArray(new String[0]);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<String> iteratorName = header.keySet().iterator();
        Iterator<DataType> iteratorType = header.values().iterator();
        int order = 0;
        while (iteratorName.hasNext()) {
            sb.append(order++);
            sb.append("|");
            sb.append(iteratorName.next());
            sb.append("|");
            sb.append(iteratorType.next().getSignature());
            if (iteratorName.hasNext()) {
                sb.append(System.lineSeparator());
            }
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TableImpl other) {
            return this.name.equals(other.name) && this.header.equals(other.header);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, header);
    }
}
