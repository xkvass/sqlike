package cz.fi.muni.pb162.sqlike.query.builder;

/**
 * Interface for building ALTER TABLE queries.
 */
public interface AlterTableQueryBuilder extends QueryBuilder {

    /**
     * Specifies the table to be altered.
     *
     * @param tableName name of the table to be altered
     * @return this query builder. Null if already called
     */
    AlterTableQueryBuilder alterTable(String tableName);

    /**
     * Renames the table to a new name.
     *
     * @param newTableName new name for the table
     * @return this query builder. Null if already called
     */
    AlterTableQueryBuilder renameTo(String newTableName);

    /**
     * Adds a new column to the table.
     *
     * @param columnName name of the new column
     * @param columnType type of the new column
     * @return this query builder.
     */
    AlterTableQueryBuilder addColumn(String columnName, String columnType);

    /**
     * Renames an existing column in the table.
     *
     * @param columnName name of the column to be renamed
     * @return this query builder.
     */
    AlterTableQueryBuilder dropColumn(String columnName);
}
