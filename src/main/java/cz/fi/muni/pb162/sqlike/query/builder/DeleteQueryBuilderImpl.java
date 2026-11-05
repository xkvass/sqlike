package cz.fi.muni.pb162.sqlike.query.builder;

import cz.fi.muni.pb162.sqlike.query.Query;
import cz.fi.muni.pb162.sqlike.query.QueryDelete;
import cz.fi.muni.pb162.sqlike.query.builder.field.Condition;
import cz.fi.muni.pb162.sqlike.query.builder.field.Field;

/**
 * Represents builder for building SQL delete query.
 *
 * @author Lubomir Vargovcik
 */
public class DeleteQueryBuilderImpl implements DeleteQueryBuilder {
    private Field tableName = null;
    private Condition where;

    @Override
    public DeleteQueryBuilder deleteFrom(Field tableName) {
        if (this.tableName != null) {
            return null;
        }
        this.tableName = tableName;
        return this;
    }

    @Override
    public Query build() {
        if (tableName == null) {
            return null;
        }
        return new QueryDelete(tableName, where);
    }

    @Override
    public DeleteQueryBuilder where(Condition condition) {
        this.where = condition;
        return this;
    }
}
