package cz.fi.muni.pb162.sqlike.query.builder;

import cz.fi.muni.pb162.sqlike.query.builder.field.Field;

/**
 * Interface for building update queries.
 * <p>
 * This interface extends the {@link WhereQueryBuilder} and {@link QueryBuilder} interfaces,
 * allowing for the construction of update queries with a WHERE clause.
 * <p>
 */
public interface UpdateQueryBuilder extends WhereQueryBuilder<UpdateQueryBuilder>, QueryBuilder {

    /**
     * Sets the table to be updated.
     *
     * @param tableName the name of the table to be updated
     * @return this UpdateQueryBuilder, null if already called
     */
    UpdateQueryBuilder update(Field tableName);

    /**
     * Sets the column to be updated with a new value.
     *
     * @param columnName the name of the column to be updated
     * @param value      the new value for the column
     * @return this UpdateQueryBuilder
     */
    UpdateQueryBuilder set(Field columnName, Object value);

}
