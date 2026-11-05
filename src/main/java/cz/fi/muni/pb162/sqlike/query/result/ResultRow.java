package cz.fi.muni.pb162.sqlike.query.result;

import java.util.Optional;

/**
 * Represents a single row in the result set
 */
public interface ResultRow {

    /**
     * Returns value of the column as integer
     *
     * @param columnName name of the column
     * @return value of the column as integer
     */
    Optional<Integer> getInt(String columnName);

    /**
     * Returns value of the column as double
     *
     * @param columnName name of the column
     * @return value of the column as double
     */
    Optional<Double> getDouble(String columnName);

    /**
     * Returns value of the column as string
     *
     * @param columnName name of the column
     * @return value of the column as string
     */
    Optional<String> getString(String columnName);

    /**
     * Returns name of the table
     *
     * @return name of the table
     */
    String getTableName();

    /**
     * Creates a string representation of a row in payload.
     *
     * @param delimiter string that values are separated by
     * @return string representation of result row
     */
    String toString(String delimiter);

}
