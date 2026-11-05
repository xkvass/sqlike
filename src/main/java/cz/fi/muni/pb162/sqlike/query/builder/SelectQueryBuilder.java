package cz.fi.muni.pb162.sqlike.query.builder;

import cz.fi.muni.pb162.sqlike.query.builder.field.Field;
import cz.fi.muni.pb162.sqlike.query.builder.field.OrderedField;

/**
 * Interface for select query builders.
 */
public interface SelectQueryBuilder extends
        QueryBuilder,
        GroupByQueryBuilder<SelectQueryBuilder>,
        WhereQueryBuilder<SelectQueryBuilder>,
        CsvExportable<SelectQueryBuilder> {

    /**
     * Adds columns to the select query. Alternative is {@link #selectFrom(Field)}.
     *
     * @param columns columns (or aggregates) to select
     * @return this query builder. Null if already called
     */
    SelectQueryBuilder select(Field... columns);

    /**
     * Adds a table to the select query.
     *
     * @param tableName name of the table
     * @return this query builder. Null if already called
     */
    SelectQueryBuilder from(Field tableName);

    /**
     * Alternative to {@link #select(Field...)}. and {@link #from(Field)}. Select of all columns in rows.
     *
     * @param tableName name of the table
     * @return this query builder. Null if already called
     * {@link #select(Field...)}. or
     * {@link #from(Field)}
     */
    SelectQueryBuilder selectFrom(Field tableName);

    /**
     * Adds a limit to the select query.
     *
     * @param limit limit of rows
     * @return this query builder. Null if already called
     */
    SelectQueryBuilder limit(int limit);

    /**
     * Adds an offset to the select query.
     *
     * @param offset offset of rows
     * @return this query builder. Null if already called
     */
    SelectQueryBuilder offset(int offset);

    /**
     * Orders the result set by the given columns.
     *
     * @param fields columns to order by
     * @return this query builder. Null if already called
     */
    SelectQueryBuilder orderBy(OrderedField... fields);


}
