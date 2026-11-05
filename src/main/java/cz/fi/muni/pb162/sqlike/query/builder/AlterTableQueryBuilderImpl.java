package cz.fi.muni.pb162.sqlike.query.builder;

import cz.fi.muni.pb162.sqlike.data.type.DataType;
import cz.fi.muni.pb162.sqlike.query.Query;
import cz.fi.muni.pb162.sqlike.query.QueryAlterTable;

import static cz.fi.muni.pb162.sqlike.data.type.DataTypeImpl.createDataType;

/**
 * Class that implements the AlterTableQueryType interface.
 *
 * @author Valeria Kvassayova
 */
public class AlterTableQueryBuilderImpl implements AlterTableQueryBuilder {
    private String tableName;
    private String newTableName;
    private String addColumn;
    private DataType columnType;
    private String dropColumn;

    @Override
    public AlterTableQueryBuilder alterTable(String tableName) {
        if (this.tableName != null) {
            return null;
        }
        this.tableName = tableName;
        return this;
    }

    @Override
    public AlterTableQueryBuilder renameTo(String newTableName) {
        if (this.newTableName != null) {
            return null;
        }
        this.newTableName = newTableName;
        return this;
    }

    @Override
    public AlterTableQueryBuilder addColumn(String columnName, String columnType) {
        this.addColumn = columnName;
        this.columnType = createDataType(columnType);
        return this;
    }

    @Override
    public AlterTableQueryBuilder dropColumn(String columnName) {
        this.dropColumn = columnName;
        return this;
    }

    @Override
    public Query build() {
        if (tableName == null || (newTableName == null && addColumn == null && dropColumn == null)) {
            return null;
        }
        return new QueryAlterTable(tableName, newTableName, addColumn, columnType, dropColumn);
    }
}
