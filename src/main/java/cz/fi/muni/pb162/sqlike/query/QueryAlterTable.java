package cz.fi.muni.pb162.sqlike.query;

import cz.fi.muni.pb162.sqlike.data.type.DataType;

/**
 * This class implements interface Query for SQL alter table query.
 *
 * @author Valeria Kvassayova
 */
public class QueryAlterTable implements Query {
    private final String tableName;
    private final String newTableName;
    private final String addColumn;
    private final DataType columnType;
    private final String dropColumn;

    /**
     * Constructor.
     *
     * @param tableName table from database
     * @param newTableName new name for table
     * @param addColumn column to be added
     * @param type type of the new column
     * @param dropColumn column to be removed
     */
    public QueryAlterTable(String tableName, String newTableName, String addColumn, DataType type, String dropColumn) {
        this.tableName = tableName;
        this.newTableName = newTableName;
        this.addColumn = addColumn;
        this.columnType = type;
        this.dropColumn = dropColumn;
    }

    public String getTableName() {
        return tableName;
    }

    public String getNewTableName() {
        return newTableName;
    }

    public String getAddColumn() {
        return addColumn;
    }

    public DataType getColumnType() {
        return columnType;
    }

    public String getDropColumn() {
        return dropColumn;
    }

    @Override
    public String toSqlString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ALTER TABLE ").append(tableName);
        if (newTableName != null) {
            builder.append(" RENAME TO ").append(newTableName).append(";");
        }
        if (addColumn != null) {
            builder.append(" ADD COLUMN ").append(addColumn)
                    .append(" ").append(columnType.getSignature()).append(";");
        }
        if (dropColumn != null) {
            builder.append(" DROP COLUMN ").append(dropColumn).append(";");
        }
        return builder.toString();
    }

    @Override
    public QueryType getQueryType() {
        if (getNewTableName() != null) {
            return QueryTypeImpl.ALTER_TABLE_RENAME_TABLE;
        }
        if (getAddColumn() != null) {
            return QueryTypeImpl.ALTER_TABLE_ADD_COLUMN;
        }
        if (getDropColumn() != null) {
            return QueryTypeImpl.ALTER_TABLE_DROP_COLUMN;
        }
        return null;
    }

}
