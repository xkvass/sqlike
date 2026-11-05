package cz.fi.muni.pb162.sqlike.query.builder;

import cz.fi.muni.pb162.sqlike.query.Query;

/**
 * Interface for query builders.
 */
public interface QueryBuilder {

    /**
     * Builds the query.
     * Here are specific requirements for each query type:
     * <p>
     * {@link CreateTableQueryBuilder#build};
     * - must contain exactly one table name
     * - must contain at least one column
     * - must contain exactly one primary key
     * <p>
     * {@link DropTableQueryBuilder#build};
     * - must contain exactly one table name
     * <p>
     * {@link InsertQueryBuilder#build};
     * - must contain exactly one table name and at least one column
     * - must contain values for all columns mentioned in queries
     * - number of values must match the number of columns
     * <p>
     * {@link SelectQueryBuilder#build};
     * - must contain exactly one table name
     * - columns are optional
     * <p>
     * {@link UpdateQueryBuilder#build};
     * - must contain exactly one table name {@link UpdateQueryBuilder#update(cz.fi.muni.pb162.sqlike.query.builder.field.Field)}
     * - must contain at least one column {@link UpdateQueryBuilder#set(cz.fi.muni.pb162.sqlike.query.builder.field.Field, Object)}
     * <p>
     * {@link DeleteQueryBuilder#build};
     * - must contain exactly one table name {@link DeleteQueryBuilder#deleteFrom(cz.fi.muni.pb162.sqlike.query.builder.field.Field)}}
     * <p>
     * {@link AlterTableQueryBuilder#build};
     * - must contain exactly one table name {@link AlterTableQueryBuilder#alterTable(String)}
     * - must contain at least one column {@link AlterTableQueryBuilder#addColumn(String, String)}
     * OR exclusively
     * - must contain at least one drop column {@link AlterTableQueryBuilder#dropColumn(String)}
     * OR exclusively
     * - must contain exactly one rename table {@link AlterTableQueryBuilder#renameTo(String)}
     * <p>
     * {@link CreateIndexQueryBuilder#build};
     *
     * @return the created query
     */
    Query build();

}
