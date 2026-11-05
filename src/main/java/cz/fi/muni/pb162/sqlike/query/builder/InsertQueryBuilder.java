package cz.fi.muni.pb162.sqlike.query.builder;

/**
 * Insert query builder. Methods have defined order of calling
 */
public interface InsertQueryBuilder extends QueryBuilder {

    /**
     * Inserts into table with given columns.
     *
     * @param table   table name
     * @param columns columns to insert
     * @return this query builder. Null if already called
     */
    InsertQueryBuilder insertInto(String table, String... columns);

    /**
     * Adds values to the insert query.
     * Can be called multiple times but for the ability to call {@link #build()} at least once.
     *
     * @param columns values to insert
     * @return this query builder.
     */
    InsertQueryBuilder values(Object... columns);

}
