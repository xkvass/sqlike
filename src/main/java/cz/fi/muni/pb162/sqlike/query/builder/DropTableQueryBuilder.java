package cz.fi.muni.pb162.sqlike.query.builder;

/**
 * Interface for drop table query builders.
 */
public interface DropTableQueryBuilder extends QueryBuilder {

    /**
     * Drops table with given name.
     *
     * @param tableName name of the table
     * @return this DropTableQueryBuilder. Null if already called
     */
    DropTableQueryBuilder dropTable(String tableName);

}
