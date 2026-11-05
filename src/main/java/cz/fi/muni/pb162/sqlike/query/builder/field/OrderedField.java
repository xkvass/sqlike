package cz.fi.muni.pb162.sqlike.query.builder.field;

import java.util.Optional;

/**
 * Represents a field that can be ordered in a query.
 * This interface extends the Field interface and adds methods for ordering.
 * It allows specifying the order (ascending or descending) of the field.
 */
public interface OrderedField extends Field {

    /**
     * Changes the order of the field to ascending.
     */
    OrderedField asc();

    /**
     * Changes the order of the field to descending.
     */
    OrderedField desc();

    /**
     * Returns the order of the field.
     *
     * @return an Optional containing true if the field is ordered ascending,
     * false if it is ordered descending, or empty if no order is specified.
     */
    Optional<Boolean> isAscending();
}
