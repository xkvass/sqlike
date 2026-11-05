package cz.fi.muni.pb162.sqlike.query.builder;

import cz.fi.muni.pb162.sqlike.query.builder.field.Condition;
import cz.fi.muni.pb162.sqlike.query.builder.field.Field;

/**
 * Interface for building a GROUP BY query.
 * <p>
 * This interface provides methods for adding
 * GROUP BY and HAVING clauses to the query.
 * </p>
 */
public interface GroupByQueryBuilder<T extends GroupByQueryBuilder<T>> {

    /**
     * Adds a HAVING clause to the query.
     *
     * @param condition the condition for the HAVING clause
     * @return this builder instance
     */
    T having(Condition condition);

    /**
     * Adds a GROUP BY clause to the query.
     *
     * @param fields the fields to group by
     * @return this builder instance
     */
    T groupBy(Field... fields);

}
