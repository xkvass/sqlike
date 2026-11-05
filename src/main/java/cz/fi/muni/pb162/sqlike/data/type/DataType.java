package cz.fi.muni.pb162.sqlike.data.type;

/**
 * Represents a datatype of a column.
 * <p>
 * Two types are considered equal if their signatures are equal.
 */
public interface DataType {

    /**
     * Returns the name of the type.
     *
     * @return name of the type (e.g. "INTEGER", "TEXT", "REAL" etc.)
     */
    String getSignature();

}
