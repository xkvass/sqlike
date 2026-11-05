package cz.fi.muni.pb162.sqlike.query;

import cz.fi.muni.pb162.sqlike.data.table.value.Value;
import cz.fi.muni.pb162.sqlike.data.table.value.ValueImpl;
import cz.fi.muni.pb162.sqlike.query.builder.field.Condition;
import cz.fi.muni.pb162.sqlike.query.builder.field.ConditionImpl;
import cz.fi.muni.pb162.sqlike.query.builder.field.Field;
import cz.fi.muni.pb162.sqlike.query.builder.field.FieldImpl;
import cz.fi.muni.pb162.sqlike.query.builder.field.FieldType;
import cz.fi.muni.pb162.sqlike.query.builder.field.OrderedField;
import cz.fi.muni.pb162.sqlike.query.builder.field.OrderedFieldImpl;

import java.util.Objects;

import static cz.fi.muni.pb162.sqlike.data.type.DataTypeImpl.createDataType;
import static java.util.Objects.compare;

/**
 * This interface provides a DSL (Domain Specific Language) for building SQL-like queries.
 * It includes methods for creating aggregate fields, column fields, and table fields.
 * The methods are static and can be used without instantiating the class.
 */
public interface Dsl {

    /**
     * Creates an aggregate field with the specified name. (count)
     *
     * @param columnName the name of the column, which will be used in the aggregate function
     * @return an instance of AggregateField
     */
    static Field count(String columnName) {
        return new FieldImpl(columnName, FieldType.COUNT);
    }

    /**
     * Creates an aggregate field with the specified name. (count) for all columns (e.g. count(*))
     *
     * @return an instance of AggregateField
     */
    static Field count() {
        return new FieldImpl("*", FieldType.COUNT);
    }

    /**
     * Creates an aggregate field with the specified name. (sum)
     *
     * @param columnName the name of the column, which will be used in the aggregate function
     * @return an instance of AggregateField
     */
    static Field sum(String columnName) {
        return new FieldImpl(columnName, FieldType.SUM);
    }

    /**
     * Creates an aggregate field with the specified name. (avg)
     *
     * @param columnName the name of the column, which will be used in the aggregate function
     * @return an instance of AggregateField
     */
    static Field avg(String columnName) {
        return new FieldImpl(columnName, FieldType.AVG);
    }

    /**
     * Creates an aggregate field with the specified name. (min)
     *
     * @param columnName the name of the column, which will be used in the aggregate function
     * @return an instance of AggregateField
     */
    static Field min(String columnName) {
        return new FieldImpl(columnName, FieldType.MIN);
    }

    /**
     * Creates an aggregate field with the specified name. (max)
     *
     * @param columnName the name of the column, which will be used in the aggregate function
     * @return an instance of AggregateField
     */
    static Field max(String columnName) {
        return new FieldImpl(columnName, FieldType.MAX);
    }

    /**
     * Creates a column field with the specified name.
     *
     * @param columnName the name of the column
     * @return an instance of ColumnField
     */
    static Field col(String columnName) {
        return new FieldImpl(columnName, FieldType.COLUMN);
    }

    static OrderedField ord(String columnName) {
        return new OrderedFieldImpl(columnName, FieldType.COLUMN);
    }

    static Field table(String tableName) {
        return new FieldImpl(tableName, FieldType.TABLE);
    }


    /**
     * Negates the given condition.
     *
     * @param condition the condition to be negated
     * @return negated condition
     */
    static Condition not(Condition condition) {
        return condition.not();
    }

    static Condition eq(Field field, Object value) {
        String fieldName = field.getFieldClass().isAggregate()
                ? field.getFieldClass().getName() + "(" + field.getFieldName() + ")" : field.getFieldName();
        return new ConditionImpl(
                row -> { return row.getValue(fieldName) != null
                        && Objects.equals(row.getValue(fieldName).getValue(), value); },
                field.toSql() + " = " + formatValue(value),
                field);
    }

    static Condition eq(Field field1, Field field2) {
        return new ConditionImpl(
                row -> Objects.equals(row.getValue(field1.getFieldName()), row.getValue(field2.getFieldName())),
                field1.toSql() + " = " + field2.toSql(),
                field1, field2);
    }

    static Condition gt(Field field, Object value) {
        String fieldName = field.getFieldClass().isAggregate()
                ? field.getFieldClass().getName() + "(" + field.getFieldName() + ")" : field.getFieldName();
        return new ConditionImpl(row ->
                compare(row.getValue(fieldName), value) > 0,
                field.toSql() + " > " + formatValue(value),
                field);
    }

    static Condition gt(Field field1, Field field2) {
        return new ConditionImpl(
                row -> compare(row.getValue(field1.getFieldName()), row.getValue(field2.getFieldName())) > 0,
                field1.toSql() + " > " + field2.toSql(),
                field1, field2);
    }

    static Condition lt(Field field, Object value) {
        String fieldName = field.getFieldClass().isAggregate()
                ? field.getFieldClass().getName() + "(" + field.getFieldName() + ")" : field.getFieldName();
        return new ConditionImpl(row ->
                compare(row.getValue(fieldName), value) < 0,
                field.toSql() + " < " + formatValue(value),
                field);
    }

    static Condition lt(Field field1, Field field2) {
        return new ConditionImpl(
                row -> compare(row.getValue(field1.getFieldName()), row.getValue(field2.getFieldName())) < 0,
                field1.toSql() + " < " + field2.toSql(),
                field1, field2);
    }

    /**
     * Compares two objects for order.
     *
     * @param a The first object to be compared.
     * @param b The second object to be compared.
     */
    private static int compare(Object a, Object b) {
        if (a == null || b == null) return (a == null && b == null) ? 0 : (a == null ? -1 : 1);
        if (a instanceof Value && b instanceof Value) {
            return ((Value) a).compareTo((Value) b);
        }
        if (a instanceof Value && b instanceof Comparable) {
            return ((ValueImpl) a).compareTo(new ValueImpl(createDataType(String.valueOf(b.getClass())), null, b));
        }
        throw new IllegalArgumentException("Values are not comparable: " + a + " and " + b);
    }

    /**
     * Formats object into its String representation.
     *
     * @param value The value of the Object.
     */
    public static String formatValue(Object value) {
        if (value == null) {
            return "NULL";
        } else if (value instanceof String) {
            String str = (String) value;
            return "'" + str + "'";
        } else if (value instanceof Number || value instanceof Boolean) {
            return value.toString();
        } else {
            throw new IllegalArgumentException("Unsupported value type: " + value.getClass());
        }
    }

}
