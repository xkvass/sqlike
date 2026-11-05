package cz.fi.muni.pb162.sqlike.query.builder;

/**
 * Query builder for creating tables..
 */
public interface CreateTableQueryBuilder extends QueryBuilder {

    /**
     * Creates a new table with given name
     *
     * @param tableName name of the table
     * @return this query builder. Null if already called
     */
    CreateTableQueryBuilder createTable(String tableName);

    /**
     * Adds a new column to the table.
     *
     * @param columnName name of the column
     * @param type       type of the column
     * @return this query builder.
     */
    CreateTableQueryBuilder column(String columnName, String type);

    /**
     * Assigns a private key constraint to column.
     *
     * @param columnName name of the column (when building query, column must exist in the query)
     * @return this query builder. Null if already called
     */
    CreateTableQueryBuilder primaryKey(String columnName);


}
