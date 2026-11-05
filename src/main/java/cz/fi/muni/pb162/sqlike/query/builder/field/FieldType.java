package cz.fi.muni.pb162.sqlike.query.builder.field;

/**
 * Represents types of fields.
 *
 * @author Lubomir Vargovcik
 */
public enum FieldType implements FieldClass {
    COLUMN("Column", false),
    TABLE("Table", false),
    SUM("SUM", true),
    COUNT("COUNT", true),
    AVG("AVG", true),
    MIN("MIN", true),
    MAX("MAX", true);

    private final String name;
    private final boolean isAggregate;

    /**
     * Constructor.
     *
     * @param name The name of the field type.
     * @param isAggregate True if the field type represents an aggregate function, otherwise false.
     */
    FieldType(String name, boolean isAggregate) {
        this.name = name;
        this.isAggregate = isAggregate;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Boolean isAggregate() {
        return isAggregate;
    }
}
