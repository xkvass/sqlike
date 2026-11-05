package cz.fi.muni.pb162.sqlike.query;

import static cz.fi.muni.pb162.sqlike.query.QueryTypeImpl.DROP_TABLE;

/**
 * This class implements interface Query for SQL drop table query
 *
 * @author Lubomir Vargovcik
 * @author Valeria Kvassayova
 */
public class QueryDropTable extends QueryImpl {
    private final String tableName;

    /**
     * Constructor.
     *
     * @param tableName Table to be dropped.
     */
    public QueryDropTable(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    @Override
    public String toSqlString() {
        return "DROP TABLE " + tableName + ";";
    }

    @Override
    public QueryType getQueryType() {
        return DROP_TABLE;
    }
}
