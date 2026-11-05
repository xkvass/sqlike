package cz.fi.muni.pb162.sqlike.execute;

import cz.fi.muni.pb162.sqlike.query.builder.*;

/**
 * Interface for managing the database. Implementations should hold the connection to the database.
 */
public interface Queries {

    /**
     * Creates a new instance of the SelectQueryBuilder. Define the body of the method.
     *
     * @return new instance of the SelectQueryBuilder
     */
    static SelectQueryBuilder selectQueryInstance() {
        return new SelectQueryBuilderImpl();
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Creates a new instance of the CreateTableQueryBuilder. Define the body of the method.
     *
     * @return new instance of the CreateTableQueryBuilder
     */
    static CreateTableQueryBuilder createTableQueryInstance() {
        return new CreateTableQueryBuilderImpl();
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Creates a new instance of the SelectQueryBuilder. Define the body of the method.
     *
     * @return new instance of the SelectQueryBuilder
     */
    static InsertQueryBuilder insertQueryInstance() {
        return new InsertQueryBuilderImpl();
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Creates a new instance of the DropTableQueryBuilder. Define the body of the method.
     *
     * @return new instance of the DropTableQueryBuilder
     */
    static DropTableQueryBuilder dropTableQueryInstance() {
        return new DropTableQueryBuilderImpl();
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Creates a new instance of the AlterTableQueryBuilder. Define the body of the method.
     *
     * @return new instance of the AlterTableQueryBuilder
     */
    static AlterTableQueryBuilder alterTableQueryInstance() {
        return new AlterTableQueryBuilderImpl();
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Creates a new instance of the CreateIndexQueryBuilder. Define the body of the method.
     *
     * @return new instance of the CreateIndexQueryBuilder
     */
    static CreateIndexQueryBuilder createIndexQueryInstance() {
        return new CreateIndexQueryBuilderImpl();
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Creates a new instance of the DropIndexQueryBuilder. Define the body of the method.
     *
     * @return new instance of the DropIndexQueryBuilder
     */
    static UpdateQueryBuilder updateQueryInstance() {
        return new UpdateQueryBuilderImpl();
        //throw new UnsupportedOperationException("Not implemented yet");
    }

    /**
     * Creates a new instance of the DeleteQueryBuilder. Define the body of the method.
     *
     * @return new instance of the DeleteQueryBuilder
     */
    static DeleteQueryBuilder deleteQueryInstance() {
        return new DeleteQueryBuilderImpl();
        //throw new UnsupportedOperationException("Not implemented yet");
    }

}
