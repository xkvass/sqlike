package cz.fi.muni.pb162.sqlike.query.builder.field;

import cz.fi.muni.pb162.sqlike.data.table.row.Row;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Represents condition in WHERE clause of SQL queries.
 *
 * @author Lubomir Vargovcik
 */
public class ConditionImpl implements Condition {
    private final Predicate<Row> predicate;
    private final Set<Field> fields;
    private final String sqlString;

    /**
     * Constructor.
     *
     * @param predicate The logical predicate for evaluation of condition.
     * @param fields The fields used in condition.
     */
    public ConditionImpl(Predicate<Row> predicate, String sqlString, Field... fields) {
        this.predicate = predicate;
        this.fields = new HashSet<>(Arrays.asList(fields));
        this.sqlString = sqlString;
    }

    /** Constructor for combining already created conditions. */
    private ConditionImpl(Predicate<Row> predicate, String sqlString, Set<Field> fields) {
        this.predicate = predicate;
        this.fields = fields;
        this.sqlString = sqlString;
    }


    @Override
    public Boolean apply(Row row) {
        return predicate.test(row);
    }

    @Override
    public Condition and(Condition condition) {
        ConditionImpl other = (ConditionImpl) condition;
        Predicate<Row> newPredicate = row -> this.apply(row) && other.apply(row);
        Set<Field> newFields = new HashSet<>(this.fields);
        newFields.addAll(other.getAssociatedFields());
        String newSql = this.toSqlString() + " AND " + other.toSqlString();
        return new ConditionImpl(newPredicate, newSql, newFields);
    }

    @Override
    public Condition or(Condition condition) {
        ConditionImpl other = (ConditionImpl) condition;
        Predicate<Row> newPredicate = row -> this.apply(row) || other.apply(row);
        Set<Field> newFields = new HashSet<>(this.fields);
        newFields.addAll(other.getAssociatedFields());
        String newSql = this.toSqlString() + " OR " + other.toSqlString();
        return new ConditionImpl(newPredicate, newSql, newFields);
    }

    @Override
    public Condition not() {
        Predicate<Row> newPredicate = row -> !this.apply(row);
        String newSql = "NOT " + this.toSqlString();
        return new ConditionImpl(newPredicate, newSql, new HashSet<>(this.fields));
    }

    @Override
    public Set<Field> getAssociatedFields() {
        return Collections.unmodifiableSet(fields);
    }

    /** Returns the SQL string representation of the condition. */
    public String toSqlString() {
        return sqlString;
    }
}
