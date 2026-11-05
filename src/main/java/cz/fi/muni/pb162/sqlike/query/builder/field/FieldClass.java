package cz.fi.muni.pb162.sqlike.query.builder.field;

/**
 * Specifies type of the field (e.g. Column, Aggregate).
 */
public interface FieldClass {

    /**
     * Returns the name of the field type.
     *
     * @return the name of the field type.
     */
    String getName();

    /**
     * Returns the name of the field type in SQL.
     *
     * @return the name of the field type in SQL.
     */
    Boolean isAggregate();
}
