package cz.fi.muni.pb162.sqlike.data.table.value;

import cz.fi.muni.pb162.sqlike.data.type.DataType;

import java.util.Objects;

import static cz.fi.muni.pb162.sqlike.data.type.DataTypeImpl.*;

/**
 * Record implementing Value interface.
 * This class represents single value associated with a column.
 *
 * @author Lubomir Vargovcik
 */
public record ValueImpl(DataType type, String name, Object value) implements Value {
    @Override
    public DataType getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String getValueAsString() {
        return value != null ? value.toString() : "";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ValueImpl other) {
            return Objects.equals(name, other.name) && Objects.equals(value, other.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    @Override
    public int compareTo(Value o) {
        if (this.getType() == o.getType()) {
            return switch (this.getType()) {
                case INTEGER -> Integer.compare((Integer) this.getValue(), (Integer) o.getValue());
                case REAL -> Double.compare((Double) this.getValue(), (Double) o.getValue());
                case TEXT -> String.CASE_INSENSITIVE_ORDER.compare((String) this.getValue(), (String) o.getValue());
                default -> throw new IllegalArgumentException("Invalid type.");
            };
        }
        throw new IllegalArgumentException("Incomparable types.");
    }
}
