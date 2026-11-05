package cz.fi.muni.pb162.sqlike.query.builder.field;

/**
 * This class represents general field in SQL query.
 *
 * @author Lubomir Vargovcik
 */
public class FieldImpl implements Field {
    private final String name;
    private final FieldClass fieldClass;
    private String alias;

    /**
     * Constructs a FieldImpl.
     *
     * @param name  The name of the field.
     * @param fieldClass The type of the field.
     */
    public FieldImpl(String name, FieldClass fieldClass) {
        this.name = name;
        this.fieldClass = fieldClass;
    }

    @Override
    public Field as(String alias) {
        this.alias = alias;
        return this;
    }

    @Override
    public String getAlias() {
        return alias;
    }

    @Override
    public String getFieldName() {
        return name;
    }

    @Override
    public FieldClass getFieldClass() {
        return fieldClass;
    }

    @Override
    public String toSql() {
        StringBuilder sb = new StringBuilder();
        if (getFieldClass().isAggregate()) {
            sb.append(getFieldClass().getName()).append("(");
        }
        sb.append(name);
        if (getFieldClass().isAggregate()) {
            sb.append(")");
        }
        return sb.toString();
    }
}
