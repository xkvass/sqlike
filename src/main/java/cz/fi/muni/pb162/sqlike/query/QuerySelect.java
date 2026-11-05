package cz.fi.muni.pb162.sqlike.query;

import cz.fi.muni.pb162.sqlike.query.builder.field.Condition;
import cz.fi.muni.pb162.sqlike.query.builder.field.ConditionImpl;
import cz.fi.muni.pb162.sqlike.query.builder.field.Field;
import cz.fi.muni.pb162.sqlike.query.builder.field.OrderedField;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static cz.fi.muni.pb162.sqlike.query.QueryTypeImpl.SELECT;

/**
 * This class implements interface Query for SQL select query.
 *
 * @author Lubomir Vargovcik
 * @author Valeria Kvassayova
 */
public class QuerySelect extends QueryImpl {
    private final Field table;
    private Field[] columns;
    private final int limit;
    private final int offset;
    private final OrderedField[] orderBy;
    private final Field[] groupBy;
    private final Condition where;
    private final Condition having;
    private final String fileName;
    private final String delimiter;
    private final boolean header;
    private final String charsetName;

    /**
     * Constructor.
     *
     * @param table field representing table
     * @param columns array of fields representing columns
     */
    public QuerySelect(Field table, Field[] columns, int limit, int offset, OrderedField[] orderBy, Field[] groupBy,
                       Condition where, Condition having, String fileName, String delimiter, boolean header,
                       String charsetName) {
        this.table = table;
        this.columns = columns;
        this.limit = limit;
        this.offset = offset;
        this.orderBy = orderBy;
        this.groupBy = groupBy;
        this.where = where;
        this.having = having;
        this.fileName = fileName;
        this.delimiter = delimiter;
        this.header = header;
        this.charsetName = charsetName;
    }

    /**
     * Get table name as a string from field table.
     *
     * @return name of table
     */
    public String getTableName() {
        return table.getFieldName();
    }

    public Field[] getColumns() {
        return columns;
    }

    public void setColumns(Field[] columns) {
        this.columns = columns;
    }

    /**
     * Get column names as an array of strings from field columns.
     *
     * @return array with names of columns
     */
    public String[] getColumnNames() {
        return Arrays.stream(columns).map(Field::getFieldName).toArray(String[]::new);
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public OrderedField[] getOrderBy() {
        return orderBy;
    }

    public Field[] getGroupBy() {
        return groupBy;
    }

    public Condition getWhere() {
        return where;
    }

    public Condition getHaving() {
        return having;
    }

    public String getFileName() {
        return fileName;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public boolean isHeader() {
        return header;
    }

    public String getCharsetName() {
        return charsetName;
    }

    @Override
    public String toSqlString() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        // Columns
        if (columns == null || columns.length == 0) {
            sb.append("* ");
        } else {
            for (Field f : columns) {
                sb.append(f.toSql());
                if (f.getAlias() != null) {
                    sb.append(" AS ");
                    sb.append(f.getAlias());
                }
                sb.append(", ");
            }
            sb.deleteCharAt(sb.length() - 2);
        }
        // FROM table
        sb.append("FROM ").append(table.getFieldName());
        // WHERE
        if (where != null) {
            sb.append(" WHERE ").append(((ConditionImpl) where).toSqlString());
        }
        // GROUP BY
        if (groupBy != null && groupBy.length > 0) {
            sb.append(" GROUP BY ").append(Arrays.stream(groupBy)
                .map(Field::toSql)
                .collect(Collectors.joining(", ")));
        }
        // HAVING
        if (having != null) {
            sb.append(" HAVING ").append(((ConditionImpl) having).toSqlString());
        }
        // ORDER BY
        if (orderBy != null) {
            sb.append(" ORDER BY");
            for (OrderedField ord : orderBy) {
                sb.append(" ").append(ord.toSql());
                if (ord.isAscending().isPresent()) {
                    if (ord.isAscending().get()) {
                        sb.append(" ASC");
                    } else {
                        sb.append(" DESC");
                    }
                }
                sb.append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        // LIMIT
        if (limit != Integer.MAX_VALUE) {
            sb.append(" LIMIT ").append(limit);
        }
        // OFFSET
        if (offset > 0) {
            sb.append(" OFFSET ").append(offset);
        }
        sb.append(";");
        return sb.toString();
    }

    @Override
    public QueryType getQueryType() {
        return SELECT;
    }
}
