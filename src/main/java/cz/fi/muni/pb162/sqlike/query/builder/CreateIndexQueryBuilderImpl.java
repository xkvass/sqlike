package cz.fi.muni.pb162.sqlike.query.builder;

import cz.fi.muni.pb162.sqlike.query.Query;
import cz.fi.muni.pb162.sqlike.query.QueryCreateIndex;
import cz.fi.muni.pb162.sqlike.query.builder.field.OrderedField;

/**
 * Represents builder for building SQL index query.
 *
 * @author Lubomir Vargovcik
 */
public class CreateIndexQueryBuilderImpl implements CreateIndexQueryBuilder {
    private String indexName = null;
    private String tableName = null;
    private OrderedField field;

    @Override
    public CreateIndexQueryBuilder createIndex(String indexName) {
        if (this.indexName != null) {
            return null;
        }
        this.indexName = indexName;
        return this;
    }

    @Override
    public CreateIndexQueryBuilder on(String tableName, OrderedField field) {
        if (this.tableName != null) {
            return null;
        }
        this.tableName = tableName;
        this.field = field;
        return this;
    }

    @Override
    public Query build() {
        if (indexName == null || tableName == null) {
            return null;
        }
        return new QueryCreateIndex(indexName, tableName, field);
    }
}
