package cz.fi.muni.pb162.sqlike.query.builder.field;

/**
 * Interface representing a field in a SQL query.
 * <p>
 * This interface provides methods to get the field name and field type.
 * </p>
 */
public interface Field {


    /**
     * Sets an alias for the field.
     *
     * @param alias the alias to set
     * @return this field instance
     */
    Field as(String alias);

    /**
     * Returns the alias of the field.
     *
     * @return the field alias
     */
    String getAlias();

    /**
     * Returns the name of represented by this field.
     *
     * @return the field name
     */
    String getFieldName();

    /**
     * Returns the type of the field. (e.g. SUM, COUNT, standard column, etc.)
     *
     * @return the field type
     */
    FieldClass getFieldClass();

    /**
     * Returns text representation of Sql field.
     *
     * @return the text representation
     */
    String toSql();
}
