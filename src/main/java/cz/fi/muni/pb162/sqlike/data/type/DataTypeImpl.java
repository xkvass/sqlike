package cz.fi.muni.pb162.sqlike.data.type;

/**
 * This enum implements the DataType interface and
 * represents type of data in the database.
 * Allowed values are: INTEGER, REAL and TEXT.
 *
 * @author Valeria Kvassayova
 */
public enum DataTypeImpl implements DataType {
    INTEGER, REAL, TEXT;

    /**
     * Returns specific value of this enum based on its string representation.
     *
     * @param name string representation of this enum
     * @return instance od DataTypeImpl
     */
    public static DataTypeImpl createDataType(String name) {
        if (name.equalsIgnoreCase("INTEGER") || name.equalsIgnoreCase("class java.lang.Integer")) {
            return INTEGER;
        } else if (name.equalsIgnoreCase("REAL") || name.equalsIgnoreCase("class java.lang.Double")) {
            return REAL;
        } else if (name.equalsIgnoreCase("TEXT") || name.equalsIgnoreCase("class java.lang.String")) {
            return TEXT;
        } else {
            return null;
        }
    }

    @Override
    public String getSignature() {
        return name().toUpperCase();
    }
}