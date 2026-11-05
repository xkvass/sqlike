package cz.fi.muni.pb162.sqlike.query;

import cz.fi.muni.pb162.sqlike.query.builder.field.Condition;
import cz.fi.muni.pb162.sqlike.query.builder.field.ConditionImpl;
import cz.fi.muni.pb162.sqlike.query.builder.field.Field;

import java.util.LinkedHashMap;
import java.util.Map;

import static cz.fi.muni.pb162.sqlike.query.QueryTypeImpl.UPDATE;

/**
 * This class implements interface Query for SQL update table query.
 *
 * @author Lubomir Vargovcik
 */
public class QueryUpdate extends QueryImpl {
    private Field tableName = null;
    private final Map<Field, Object> updates;
    private Condition where = null;

    /**
     * Constructor.
     *
     * @param tableName The name of the table to delete from.
     * @param updates The updates to be done on table.
     * @param where The condition from where clause.
     */
    public QueryUpdate(Field tableName, Map<Field, Object> updates, Condition where) {
        this.tableName = tableName;
        this.updates = new LinkedHashMap<>(updates);
        this.where = where;
    }

    public String getTableName() {
        return tableName.getFieldName();
    }

    public Map<Field, Object> getUpdates() {
        return updates;
    }

    public Condition getWhere() {
        return where;
    }

    @Override
    public String toSqlString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ").append(tableName.getFieldName()).append(" SET ");
        boolean first = true;
        for (Map.Entry<Field, Object> entry : updates.entrySet()) {
            if (!first) {
                sb.append(", ");
            }
            first = false;
            sb.append(entry.getKey().getFieldName()).append(" = ").append(Dsl.formatValue(entry.getValue()));
        }
        if (where != null) {
            sb.append(" WHERE ").append(((ConditionImpl) where).toSqlString());
        }
        sb.append(";");
        return sb.toString();
    }

    @Override
    public QueryType getQueryType() {
        return UPDATE;
    }
}
