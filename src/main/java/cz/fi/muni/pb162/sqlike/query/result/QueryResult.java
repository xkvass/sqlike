package cz.fi.muni.pb162.sqlike.query.result;

import java.util.Optional;

/**
 * Represents result of a query.
 * <p>
 * Two QueryResults are considered to be same if they have the same status and payload.
 * <p>
 * String representation of QueryResult is defined as String representation of payload elements separated by new line.
 */
public interface QueryResult {

    /**
     * Returns status of the query.
     *
     * @return status of the query (OK, Error)
     */
    Status status();

    /**
     * Returns payload of the query.
     *
     * @return payload of the query. It is an iterable of ResultRow. Empty if the query failed or there is no payload.
     */
    Optional<Iterable<ResultRow>> payload();

    /**
     * Returns number of rows affected by the query.
     *
     * @return number of rows affected by the query
     */
    int rowsAffected();

    /**
     * Returns error message if the query failed. Empty if the query was successful.
     *
     * @return error message if the query failed. Empty if the query was successful.
     */
    Optional<String> errorMessage();

}
