package cz.fi.muni.pb162.sqlike.query;

import cz.fi.muni.pb162.sqlike.query.builder.field.Field;
import cz.fi.muni.pb162.sqlike.query.builder.field.OrderedField;

import static cz.fi.muni.pb162.sqlike.query.QueryTypeImpl.CREATE_INDEX;

/**
 * This class implements interface Query for SQL create index query.
 *
 * @author Lubomir Vargovcik
 */
public class QueryCreateIndex extends QueryImpl {
    private final String indexName;
    private final String tableName;
    private final OrderedField field;

    /**
     * Constructor.
     *
     * @param indexName The name of the index.
     * @param tableName The name of the table to delete from.
     * @param field The field for indexing.
     */
    public QueryCreateIndex(String indexName, String tableName, OrderedField field) {
        this.indexName = indexName;
        this.tableName = tableName;
        this.field = field;
    }

    public String getIndexName() {
        return indexName;
    }

    public String getTableName() {
        return tableName;
    }

    public OrderedField getField() {
        return field;
    }

    @Override
    public String toSqlString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE INDEX ")
                .append(indexName)
                .append(" ON ")
                .append(tableName)
                .append(" (")
                .append(field.getFieldName());
        if (field.isAscending().isPresent()) {
            sb.append(field.isAscending().get() ? " ASC" : " DESC");
        }
        sb.append(");");
        return sb.toString();
    }

    @Override
    public QueryType getQueryType() {
        return CREATE_INDEX;
    }
}
