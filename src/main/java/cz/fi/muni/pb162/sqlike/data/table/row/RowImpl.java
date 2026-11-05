package cz.fi.muni.pb162.sqlike.data.table.row;

import cz.fi.muni.pb162.sqlike.data.table.value.Value;
import cz.fi.muni.pb162.sqlike.data.type.DataType;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Class implementing the Row interface.
 * This class represents single row of Values in table.
 *
 * @author Lubomir Vargovcik
 */
public class RowImpl implements Row {
    /** Map of column names and values. */
    private final Map<String, Value> values;

    /**
     * Constructor that creates empty row.
     */
    public RowImpl() {
        values = new LinkedHashMap<>();
    }

    /** Constructor.
     *
     * @param values Row will contain these values.
     */
    public RowImpl(Map<String, Value> values) {
        this.values = values;
    }

    public Map<String, Value> getRow() {
        return values;
    }

    /**
     * Creates a map representing header based on values in a row.
     *
     * @return map with name of column as a key and ist DataType as value
     */
    public Map<String, DataType> getHeader() {
        Map<String, DataType> result = new LinkedHashMap<>();
        for (Map.Entry<String, Value> entry : this.values.entrySet()) {
            result.put(entry.getKey(), entry.getValue().getType());
        }
        return result;
    }

    @Override
    public Value[] getValues() {
        return values.values().toArray(new Value[0]);
    }

    @Override
    public Value getValue(String name) {
        return values.get(name);
    }

    public void setValue(String name, Value value) {
        values.put(name, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator<Value> iterator = values.values().iterator();

        while (iterator.hasNext()) {
            Value value = iterator.next();

            // safe fetch of signature
            String sig = value.getType() == null ? "" : value.getType().getSignature();
            boolean isText = "TEXT".equals(sig);

            if (isText) sb.append("'");

            // safe fetch of the raw value
            Object raw = value.getValue();
            sb.append(raw != null ? raw.toString() : "NULL");

            if (isText) sb.append("'");

            if (iterator.hasNext()) {
                sb.append("|");
            }
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof RowImpl other) {
            return Arrays.equals(this.getValues(), other.getValues());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.getValues());
    }
}
