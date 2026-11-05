package cz.fi.muni.pb162.sqlike.query.builder;

import cz.fi.muni.pb162.sqlike.query.builder.field.Field;

/**
 * Interface for building DELETE queries.
 * <p>
 * This interface extends the {@link WhereQueryBuilder} interface, allowing
 * for the addition of WHERE conditions to the DELETE query.
 * </p>
 */
public interface DeleteQueryBuilder extends WhereQueryBuilder<DeleteQueryBuilder>, QueryBuilder {

    /**
     * Specifies the table from which to delete records.
     *
     * @param tableName the name of the table
     * @return this builder instance. Null if already called
     */
    DeleteQueryBuilder deleteFrom(Field tableName);

}
