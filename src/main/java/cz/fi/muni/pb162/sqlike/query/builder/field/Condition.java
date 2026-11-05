package cz.fi.muni.pb162.sqlike.query.builder.field;

import cz.fi.muni.pb162.sqlike.data.table.row.Row;

import java.util.Set;

/**
 * Interface for conditions in the WHERE clause of a SQL query.
 * <p>
 * This interface defines methods for accepting a stream of rows, a collection of rows,
 * or a single row, and for combining conditions using logical operators (AND, OR, NOT).
 */
public interface Condition {

    /**
     * Accepts a single row and evaluates the condition on it.
     *
     * @param row the row to evaluate
     */
    Boolean apply(Row row);

    /**
     * Combines this condition with another condition using the AND operator.
     * <p>
     * Example: SELECT * FROM table WHERE field_1 = 1 AND field_2 = 2;
     *
     * @param condition the condition to combine with
     * @return a new condition representing the combined condition
     */
    Condition and(Condition condition);

    /**
     * Combines this condition with another condition using the OR operator.
     * <p>
     * Example: SELECT * FROM table WHERE field_1 = 1 OR field_2 = 2;
     *
     * @param condition the condition to combine with
     * @return a new condition representing the combined condition
     */
    Condition or(Condition condition);

    /**
     * Negates this condition using the NOT operator.
     * <p>
     * Example: SELECT * FROM table WHERE NOT (field_1 = 1);
     *
     * @return a new condition representing the negated condition
     */
    Condition not();

    /**
     * Returns a set of fields associated with this condition.
     * <p>
     * This method is used to determine which fields are involved in the condition
     * Can help you to identify the fields that are being filtered or compared.
     *
     * @return a set of fields associated with this condition
     */
    Set<Field> getAssociatedFields();
}
