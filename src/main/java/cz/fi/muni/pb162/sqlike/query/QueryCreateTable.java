package cz.fi.muni.pb162.sqlike.query;

import cz.fi.muni.pb162.sqlike.data.type.DataType;

import java.util.Map;

import static cz.fi.muni.pb162.sqlike.query.QueryTypeImpl.CREATE_TABLE;

/**
* This class implements interface Query for SQL create table query
 *
 * @author Lubomir Vargovcik
 * @author Valeria Kvassayova
 */
public class QueryCreateTable extends QueryImpl {
    private final String tableName;
    private final Map<String, DataType> header;
    private final String primaryKey;

    /**
     * Constructor.
     *
     * @param header Header of table.
     * @param tableName Name of table.
     * @param primaryKey Name of primary column.
     */
    public QueryCreateTable(String tableName, Map<String, DataType> header, String primaryKey) {
        this.tableName = tableName;
        this.header = header;
        this.primaryKey = primaryKey;
    }

    public String getTableName() {
        return tableName;
    }

    public Map<String, DataType> getHeader() {
        return header;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    @Override
    public String toSqlString() {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE ");
        builder.append(tableName);
        builder.append(" (");
        for (Map.Entry<String, DataType> entry : header.entrySet()) {
            if (primaryKey.equals(entry.getKey())) {
                builder.append(entry.getKey());
                builder.append(" ");
                builder.append(entry.getValue().getSignature());
                builder.append(" PRIMARY KEY");
                builder.append(", ");
            }
        }
        for (Map.Entry<String, DataType> entry : header.entrySet()) {
            if (!primaryKey.equals(entry.getKey())) {
                builder.append(entry.getKey());
                builder.append(" ");
                builder.append(entry.getValue().getSignature());
                builder.append(", ");
            }
        }
        builder.delete(builder.length() - 2, builder.length());
        builder.append(");");
        return builder.toString();
    }

    @Override
    public QueryType getQueryType() {
        return CREATE_TABLE;
    }
}
