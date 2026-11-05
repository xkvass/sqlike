package cz.fi.muni.pb162.sqlike.data.table.value;

import cz.fi.muni.pb162.sqlike.data.type.DataType;

/**
 * Interface representing column and its value
 * <p>
 * Values are considered same if they have same type and value
 */
public interface Value extends Comparable<Value> {

    /**
     * Returns type of the value
     *
     * @return type of the value
     */
    DataType getType();

    /**
     * Returns name of the column
     *
     * @return name of the column
     */
    String getName();

    /**
     * Returns value of the column. For general purpose it is Object
     *
     * @return value of the column
     */
    Object getValue();

    /**
     * @return string representation of value, empty string if value is null
     */
    String getValueAsString();
}
