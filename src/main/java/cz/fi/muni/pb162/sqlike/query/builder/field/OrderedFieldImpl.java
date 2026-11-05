package cz.fi.muni.pb162.sqlike.query.builder.field;

import java.util.Optional;

import static cz.fi.muni.pb162.sqlike.query.builder.field.FieldType.COLUMN;

/**
 * Represents an ordered field.
 *
 * @author Lubomir Vargovcik
 */
public class OrderedFieldImpl extends FieldImpl implements OrderedField {
    private Optional<Boolean> ascending = Optional.empty();

    /**
     * Constructor.
     *
     * @param name  The name of the field.
     * @param fieldClass The type of the field.
     */
    public OrderedFieldImpl(String name, FieldClass fieldClass) {
        super(name, fieldClass);
    }

    @Override
    public OrderedField asc() {
        this.ascending = Optional.of(true);
        return this;
    }

    @Override
    public OrderedField desc() {
        this.ascending = Optional.of(false);
        return this;
    }

    @Override
    public Optional<Boolean> isAscending() {
        return ascending;
    }

    @Override
    public OrderedField as(String alias) {
        super.as(alias);
        return this;
    }
}
