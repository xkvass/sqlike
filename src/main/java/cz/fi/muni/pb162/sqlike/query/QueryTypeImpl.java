package cz.fi.muni.pb162.sqlike.query;

/**
 * Enum that represents a QueryType instance.
 * QueryType is initialized by its name.
 *
 * @author Valeria Kvassayova
 * @author Lubomir Vargovcik
 */
public enum QueryTypeImpl implements QueryType {
    SELECT,
    INSERT,
    CREATE_TABLE,
    DROP_TABLE,
    UPDATE,
    DELETE,
    CREATE_INDEX,
    ALTER_TABLE_ADD_COLUMN,
    ALTER_TABLE_DROP_COLUMN,
    ALTER_TABLE_RENAME_TABLE
    ;

    @Override
    public String getQueryTypeName() {
        return name().toUpperCase();
    }
}
