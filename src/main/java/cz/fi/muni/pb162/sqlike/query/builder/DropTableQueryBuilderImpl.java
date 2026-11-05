package cz.fi.muni.pb162.sqlike.query.builder;

import cz.fi.muni.pb162.sqlike.query.Query;
import cz.fi.muni.pb162.sqlike.query.QueryDropTable;

/**
 * This class implements DropTableQueryBuilder interface
 *
 * @author Lubomir Vargovcik
 */
public class DropTableQueryBuilderImpl implements DropTableQueryBuilder {
    private String tableName;

    @Override
    public DropTableQueryBuilder dropTable(String tableName) {
        if (this.tableName != null) { return null; }
        this.tableName = tableName;
        return this;
    }

    @Override
    public Query build() {
        if (tableName == null || tableName.isEmpty()) { return null; }
        return new QueryDropTable(tableName);
    }
}
