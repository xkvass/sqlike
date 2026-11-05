package cz.fi.muni.pb162.sqlike.query.builder;

import cz.fi.muni.pb162.sqlike.query.builder.field.Condition;

/**
 * Interface for building a WHERE query. Not intended to be used directly.
 *
 * @param <T> the type of the builder using this
 */
public interface WhereQueryBuilder<T extends WhereQueryBuilder<T>> {

    /**
     * Adds a WHERE clause to the query.
     *
     * @param condition the condition for the WHERE clause
     * @return this builder instance
     */
    T where(Condition condition);

}
