package cz.fi.muni.pb162.sqlike.query.builder;

import cz.fi.muni.pb162.sqlike.query.Query;
import cz.fi.muni.pb162.sqlike.query.QuerySelect;
import cz.fi.muni.pb162.sqlike.query.builder.field.Condition;
import cz.fi.muni.pb162.sqlike.query.builder.field.Field;
import cz.fi.muni.pb162.sqlike.query.builder.field.OrderedField;

/**
 * This class implements SelectQueryBuilder interface.
 *
 * @author Lubomir Vargovcik
 */
public class SelectQueryBuilderImpl implements SelectQueryBuilder {
    private Field table;
    private Field[] columns;

    // Attributes for advanced queries
    private int limit = Integer.MAX_VALUE;
    private int offset = 0;
    private OrderedField[] orderBy;
    private Field[] groupBy;
    private Condition where;
    private Condition having;

    // Attributes for export to csv
    private String fileName;
    private String delimiter;
    private boolean header;
    private String charsetName;

    @Override
    public SelectQueryBuilder select(Field... columns) {
        if (this.columns != null) { return null; }
        this.columns = columns;
        return this;
    }

    @Override
    public SelectQueryBuilder from(Field tableName) {
        if (this.table != null) { return null; }
        this.table = tableName;
        return this;
    }

    @Override
    public SelectQueryBuilder selectFrom(Field tableName) {
        if (this.table != null || this.columns != null) { return null; }
        this.table = tableName;
        this.columns = new Field[0];
        return this;
    }

    @Override
    public Query build() {
        if (table == null || columns == null) { return null; }
        return new QuerySelect(table, columns, limit, offset, orderBy, groupBy, where, having,
                fileName, delimiter, header, charsetName);
    }

    @Override
    public SelectQueryBuilder limit(int limit) {
        if (this.limit != Integer.MAX_VALUE) { return null; }
        this.limit = limit;
        return this;
    }

    @Override
    public SelectQueryBuilder offset(int offset) {
        if (this.offset != 0) { return null; }
        this.offset = offset;
        return this;
    }

    @Override
    public SelectQueryBuilder orderBy(OrderedField... fields) {
        this.orderBy = fields;
        return this;
    }

    @Override
    public SelectQueryBuilder having(Condition condition) {
        this.having = condition;
        return this;
    }

    @Override
    public SelectQueryBuilder groupBy(Field... fields) {
        this.groupBy = fields;
        return this;
    }

    @Override
    public SelectQueryBuilder where(Condition condition) {
        this.where = condition;
        return this;
    }

    @Override
    public SelectQueryBuilder exportToCsv(String fileName) {
        return this.exportToCsv(fileName, ",", true, "UTF-8");
    }

    @Override
    public SelectQueryBuilder exportToCsv(String fileName, String delimiter) {
        return this.exportToCsv(fileName, delimiter, true, "UTF-8");
    }

    @Override
    public SelectQueryBuilder exportToCsv(String fileName, String delimiter, boolean withHeader) {
        return this.exportToCsv(fileName, delimiter, withHeader, "UTF-8");
    }

    @Override
    public SelectQueryBuilder exportToCsv(String fileName, String delimiter, boolean withHeader, String charsetName) {
        this.fileName = fileName;
        this.delimiter = delimiter;
        this.header = withHeader;
        this.charsetName = charsetName;
        return this;
    }
}
