package cz.fi.muni.pb162.sqlike.query.builder;

import cz.fi.muni.pb162.sqlike.data.table.row.RowImpl;
import cz.fi.muni.pb162.sqlike.data.table.value.Value;
import cz.fi.muni.pb162.sqlike.data.table.value.ValueImpl;
import cz.fi.muni.pb162.sqlike.data.type.DataType;
import cz.fi.muni.pb162.sqlike.query.Query;
import cz.fi.muni.pb162.sqlike.query.QueryInsert;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static cz.fi.muni.pb162.sqlike.data.type.DataTypeImpl.createDataType;

/**
 * This class implements InsertQueryBuilder interface.
 *
 * @author Lubomir Vargovcik
 */
public class InsertQueryBuilderImpl implements InsertQueryBuilder {
    private String tableName = null;
    private String[] columnNames;
    private List<RowImpl> rows;

    @Override
    public InsertQueryBuilder insertInto(String table, String... columns) {
        if (tableName != null) { return null; }
        this.tableName = table;
        this.columnNames = columns;
        this.rows = new ArrayList<>();
        return this;
    }

    @Override
    public InsertQueryBuilder values(Object... values) {
        if (tableName == null || columnNames == null || columnNames.length != values.length) {
            return this;
        }

        Map<String, Value> rowValues = new LinkedHashMap<>();
        for (int i = 0; i < values.length; i++) {
            Object value = values[i];
            DataType type = null;
            if (value instanceof Integer) {
                type = createDataType("INTEGER");
            } else if (value instanceof String) {
                type = createDataType("TEXT");
            } else if (value instanceof Float || value instanceof Double) {
                type = createDataType("REAL");
            } else if (value != null) {
                throw new IllegalArgumentException("INSERT ERROR: Invalid type."); // Return null if value is not String, Integer, or Float
            }
            rowValues.put(columnNames[i], new ValueImpl(type, columnNames[i], value));
        }
        this.rows.add(new RowImpl(rowValues));
        return this;
    }

    @Override
    public Query build() {
        if (tableName == null || columnNames == null || rows.isEmpty()) {
            return null;
        }
        return new QueryInsert(tableName, columnNames, rows);
    }
}
