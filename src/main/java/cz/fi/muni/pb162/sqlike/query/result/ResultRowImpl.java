package cz.fi.muni.pb162.sqlike.query.result;

import cz.fi.muni.pb162.sqlike.data.table.Table;
import cz.fi.muni.pb162.sqlike.data.table.value.Value;
import cz.fi.muni.pb162.sqlike.data.type.DataType;
import cz.fi.muni.pb162.sqlike.query.builder.field.Field;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static cz.fi.muni.pb162.sqlike.data.type.DataTypeImpl.*;

/**
 * This interface represent ResultRow in QueryResult.
 * It is linked to a table and holds its values in an instance of Map<@Field,Value>.
 *
 * @author Valeria Kvassayova
 */
public class ResultRowImpl implements ResultRow {
    private final Table table;
    private final Map<Field, Value> values;

    /**
     * Constructor.
     *
     * @param table table from which the row is
     * @param values map of values
     */
    public ResultRowImpl(Table table, Map<Field, Value> values) {
        this.table = table;
        this.values = values;
    }

    public Map<Field, Value> getValues() {
        return values;
    }

    /**
     * Returns columns as Fields linked with its DataType.
     *
     * @return map of Field as key and DataType as value
     */
    public Map<Field, DataType> getColumns() {
        Map<Field, DataType> columns = new LinkedHashMap<>();
        for (Map.Entry<Field, Value> entry : values.entrySet()) {
            columns.putIfAbsent(entry.getKey(), entry.getValue().getType());
        }
        return columns;
    }

    @Override
    public Optional<Integer> getInt(String columnName) {
        for (Map.Entry<Field, Value> entry : values.entrySet()) {
            String fieldName = entry.getKey().getAlias() != null ? entry.getKey().getAlias(): entry.getKey().getFieldName();
            if (fieldName.equals(columnName) && entry.getValue().getType() == INTEGER) {
                return Optional.of((Integer) entry.getValue().getValue());
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Double> getDouble(String columnName) {
        for (Map.Entry<Field, Value> entry : values.entrySet()) {
            String fieldName = entry.getKey().getAlias() != null ? entry.getKey().getAlias(): entry.getKey().getFieldName();
            if (fieldName.equals(columnName) && entry.getValue().getType() == REAL) {
                return Optional.of((Double) entry.getValue().getValue());
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getString(String columnName) {
        for (Map.Entry<Field, Value> entry : values.entrySet()) {
            String fieldName = entry.getKey().getAlias() != null ? entry.getKey().getAlias(): entry.getKey().getFieldName();
            if (fieldName.equals(columnName) && entry.getValue().getType() == TEXT) {
                return Optional.of((String) entry.getValue().getValue());
            }
        }
        return Optional.empty();
    }

    @Override
    public String getTableName() {
        return table.getName();
    }

    @Override
    public String toString(String delimiter) {
        StringBuilder sb = new StringBuilder();
        Iterator<Value> iterator = values.values().iterator();
        while (iterator.hasNext()) {
            Value value = iterator.next();
            sb.append(value.getValueAsString());
            if (iterator.hasNext()) {
                sb.append(delimiter);
            }
        }
        return sb.toString();
    }
}
