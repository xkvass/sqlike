package cz.fi.muni.pb162.sqlike.query;

/**
 * Abstract class implementing general Query.
 *
 * @author Lubomir Vargovcik
 */
public abstract class QueryImpl implements Query{
    /**
     * Returns the SQL string representation of the query.
     *
     * @return SQL string representation of the query
     */
    public abstract String toSqlString();

    /**
     * Returns the type of the query.
     *
     * @return type of the query
     */
    public abstract QueryType getQueryType();

    @Override
    public boolean equals(Object obj){
        if(obj instanceof QueryImpl q){
            return q.toSqlString().equals(toSqlString());
        }
        return false;
    }

    @Override
    public int hashCode(){
        return toSqlString().hashCode();
    }
}
