package cz.fi.muni.pb162.sqlike.query.builder;

import cz.fi.muni.pb162.sqlike.query.Query;
import cz.fi.muni.pb162.sqlike.query.QueryUpdate;
import cz.fi.muni.pb162.sqlike.query.builder.field.Condition;
import cz.fi.muni.pb162.sqlike.query.builder.field.Field;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents builder for building SQL update query.
 *
 * @author Lubomir Vargovcik
 */
public class UpdateQueryBuilderImpl implements UpdateQueryBuilder {
    private Field tableName = null;
    private final Map<Field, Object> updates = new LinkedHashMap<>();
    private Condition where = null;

    @Override
    public UpdateQueryBuilder update(Field tableName) {
        if (this.tableName != null) {
            return null;
        }
        this.tableName = tableName;
        return this;
    }

    @Override
    public UpdateQueryBuilder set(Field columnName, Object value) {
        updates.put(columnName, value);
        return this;
    }

    @Override
    public Query build() {
        if (tableName == null || updates.isEmpty()) {
           return null;
        }
        return new QueryUpdate(tableName, updates, where);
    }

    @Override
    public UpdateQueryBuilder where(Condition condition) {
        this.where = condition;
        return this;
    }
}
