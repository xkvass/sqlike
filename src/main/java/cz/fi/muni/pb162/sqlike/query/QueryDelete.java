package cz.fi.muni.pb162.sqlike.query;

import cz.fi.muni.pb162.sqlike.query.builder.field.Condition;
import cz.fi.muni.pb162.sqlike.query.builder.field.ConditionImpl;
import cz.fi.muni.pb162.sqlike.query.builder.field.Field;

import static cz.fi.muni.pb162.sqlike.query.QueryTypeImpl.DELETE;

/**
 * This class implements interface Query for SQL delete table query.
 *
 * @author Lubomir Vargovcik
 */
public class QueryDelete extends QueryImpl {
    private final Field tableName;
    private final Condition where;

    /**
     * Constructor.
     *
     * @param tableName The name of the table to delete from.
     * @param where The condition from where clause.
     */
    public QueryDelete(Field tableName, Condition where) {
        this.tableName = tableName;
        this.where = where;
    }

    /**
     * Returns name of the table field.
     * @return name of a table
     */
    public String getTableName() {
        return tableName.getFieldName();
    }

    public Condition getWhere() {
        return where;
    }

    @Override
    public String toSqlString() {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ").append(tableName.getFieldName());
        if (where != null) {
            sb.append(" WHERE ").append(((ConditionImpl) where).toSqlString());
        }
        sb.append(";");
        return sb.toString();
    }

    @Override
    public QueryType getQueryType() {
        return DELETE;
    }
}
