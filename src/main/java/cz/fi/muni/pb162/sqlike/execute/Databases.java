package cz.fi.muni.pb162.sqlike.execute;

import cz.fi.muni.pb162.sqlike.data.database.Database;
import cz.fi.muni.pb162.sqlike.data.database.DatabaseImpl;

public interface Databases {

    /**
     * Creates new instance for Database (mainly for test purposes).
     *
     * @return new instance of Database
     */
    static Database instance(String name) {
        return new DatabaseImpl(name);
        //throw new UnsupportedOperationException("Not implemented");
    }

}
