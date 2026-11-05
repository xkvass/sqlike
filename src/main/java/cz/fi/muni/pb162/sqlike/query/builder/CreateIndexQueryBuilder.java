package cz.fi.muni.pb162.sqlike.query.builder;

import cz.fi.muni.pb162.sqlike.query.builder.field.OrderedField;

/**
 * Interface for creating an index.
 * <p>
 * This interface provides methods
 * to create an index on a specified table with specified fields.
 * </p>
 */
public interface CreateIndexQueryBuilder extends QueryBuilder {

    /**
     * Creates an index with the specified name.
     *
     * @param indexName the name of the index
     * @return this builder instance.
     */
    CreateIndexQueryBuilder createIndex(String indexName);

    /**
     * Specifies the table on which the index will be created.
     *
     * @param tableName the table on which the index will be created
     * @param field     the fields to be included in the index
     * @return this builder instance.
     */
    CreateIndexQueryBuilder on(String tableName, OrderedField field);

}
