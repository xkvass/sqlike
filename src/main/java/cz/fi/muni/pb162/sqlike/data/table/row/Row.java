package cz.fi.muni.pb162.sqlike.data.table.row;

import cz.fi.muni.pb162.sqlike.data.table.value.Value;

/**
 * Row of a table
 *
 * @see cz.fi.muni.pb162.sqlike.data.table.Table
 * <p>
 * Two rows are considered equal if they have the equal {@link Value}s in the same order.
 * <p>
 * String representation of the row is a concatenation of its values of values :) separated by a | character.
 * For example:
 * <pre>
 * 1|Alice Johnson|30|HR|50000
 * </pre>
 */
public interface Row {

    /**
     * Returns values of the row
     *
     * @return values of the row
     */
    Value[] getValues();

    /**
     * Returns value of column of the row by column name
     *
     * @param name name of the column
     * @return value of the column in the row
     */
    Value getValue(String name);
}
