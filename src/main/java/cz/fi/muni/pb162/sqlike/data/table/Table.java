package cz.fi.muni.pb162.sqlike.data.table;

import cz.fi.muni.pb162.sqlike.data.table.row.Row;
import cz.fi.muni.pb162.sqlike.query.builder.field.Condition;

import java.util.Map;

/**
 * Represents a table in a database.
 * Encapsulates information about the table's name, columns and rows.
 * <p>
 * Two tables are considered equal if they have the same name, columns names and column data types.
 * <p>
 * <p>
 * <p>
 * String representation of the table is in the following format:
 *
 * <pre>
 * {orderOfColumn}|{columnName}|{columnType}
 * </pre>
 * <p>
 * each separated by new line character.
 * for example:
 *
 * <pre>
 * 0|id|INTEGER
 * 1|name|TEXT
 * 2|age|INTEGER
 * 3|department|TEXT
 * 4|salary|REAL
 * </pre>
 *
 * @see Row Row of the table
 */
public interface Table {

    /**
     * Inserts a new row into the table.
     * The row must contain values for all columns of the table.
     * The columns of the row must match the columns of the table (datatype and name).
     *
     * @param row row to insert
     * @return rows affected (0 if no rows were inserted)
     */
    int insert(Row[] row);

    /**
     * Retrieves all rows from the table. Can retrieve only selected columns.
     *
     * @param columns columns to select (if empty, all columns are selected)
     * @return all rows in the table
     */
    Row[] select(String... columns);

    /**
     * Deletes rows matching the given condition.
     *
     * @param condition condition that rows must satisfy to be deleted
     * @return the number of deleted rows
     */
    public int delete(Condition condition);

    /**
     * Updates rows matching the given condition.
     *
     * @param updates map of column names to their new values
     * @param condition condition that rows must satisfy to be updated
     * @return the number of updated rows
     */
    public int update(Map<String, Object> updates, Condition condition);

    /**
     * Retrieves the name of the table.
     *
     * @return table name
     */
    String getName();

    /**
     * Retrieves the names of primary key columns.
     *
     * @return primary key columns
     */
    String getPrimaryKeyColumnName();

    /**
     * Retrieves the definition of columns of the table.
     *
     * @return column definitions
     */
    String[] getColumnNames();

}
