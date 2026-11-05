package cz.fi.muni.pb162.sqlike.data.database;

import cz.fi.muni.pb162.sqlike.query.Query;
import cz.fi.muni.pb162.sqlike.query.result.QueryResult;

/**
 * Represents an entire database. Encapsulates information about the database's name and tables.
 * <p>
 * Two databases are considered equal if they have the same name.
 */
public interface Database {

    /**
     * Executes the given query and returns the result.
     *
     * @param query query to execute
     * @return result of the query
     */
    QueryResult execute(Query query);

    /**
     * Retrieves the name of the database.
     *
     * @return database name
     */
    String getName();

}
