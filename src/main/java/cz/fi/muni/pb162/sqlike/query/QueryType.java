package cz.fi.muni.pb162.sqlike.query;

/**
 * Represents type of query.
 */
public interface QueryType {

    /**
     * Returns name of the query type
     *
     * @return name of the query type (SELECT, INSERT, CREATE_TABLE, DROP_TABLE)
     */
    String getQueryTypeName();

}
