package cz.fi.muni.pb162.sqlike.query.builder;

import cz.fi.muni.pb162.sqlike.data.type.DataType;
import cz.fi.muni.pb162.sqlike.query.Query;
import cz.fi.muni.pb162.sqlike.query.QueryCreateTable;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static cz.fi.muni.pb162.sqlike.data.type.DataTypeImpl.createDataType;

/**
 * Class that implements the CreateTableQueryType interface.
 *
 * @author Valeria Kvassayova
 */
public class CreateTableQueryBuilderImpl implements CreateTableQueryBuilder {
    private String name;
    private final Map<String, DataType> header = new LinkedHashMap<>();
    private String primaryKeyColumn;
    private boolean error = false;

    @Override
    public CreateTableQueryBuilder createTable(String tableName) {
        if (this.name != null) {
            error = true;
            return this;
        }
        this.name = tableName;
        return this;
    }

    @Override
    public CreateTableQueryBuilder column(String columnName, String type) {
        if (this.name == null) {
            error = true;
            return this;
        }
        DataType dataType = createDataType(type);
        if (dataType == null) {
            error = true;
            return this;
        }
        header.put(columnName, createDataType(type));
        return this;
    }

    @Override
    public CreateTableQueryBuilder primaryKey(String columnName) {
        if (!header.containsKey(columnName) || primaryKeyColumn != null) {
            error = true;
            return this;
        }
        this.primaryKeyColumn = columnName;
        return this;
    }

    @Override
    public Query build() {
        if (name == null || primaryKeyColumn == null || header.isEmpty()) {
            return null;
        }
        if (error) {
            throw new IllegalArgumentException("ERROR: Incorrect use of QueryBuilder.");
        }
        return new QueryCreateTable(name, Collections.unmodifiableMap(new LinkedHashMap<>(header)), primaryKeyColumn);
    }
}
