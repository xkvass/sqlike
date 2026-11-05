package cz.fi.muni.pb162.sqlike.query;

public enum SqlQueryType implements QueryType {
    CREATE_TABLE,
    DROP_TABLE,
    INSERT,
    SELECT,
    UPDATE,
    DELETE,
    CREATE_INDEX,
    ALTER_TABLE,
    ;

    @Override
    public String getQueryTypeName() {
        return name().toUpperCase();
    }
}
