package cz.fi.muni.pb162.sqlike.query.builder;

import cz.fi.muni.pb162.sqlike.execute.Queries;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CreateTableQueryBuilderTest implements QueryBuilderTest {

    @Test
    public void shouldBuildQuery() {
        assertNotNull(Queries.createTableQueryInstance()
                .createTable("table1")
                .column("column1", "INTEGER")
                .column("column2", "REAL")
                .column("column3", "TEXT")
                .primaryKey("column1")
                .build());

        assertNotNull(Queries.createTableQueryInstance()
                .createTable("table1")
                .column("column1", "TEXT")
                .primaryKey("column1")
                .build());

        assertNotNull(Queries.createTableQueryInstance()
                .createTable("table1")
                .column("column1", "INTEGER")
                .primaryKey("column1")
                .column("column2", "REAL")
                .column("column3", "TEXT")
                .build());

    }

    @Test
    public void shouldThrowException() {
        assertThrows(
                Exception.class,
                () -> Queries.createTableQueryInstance()
                        .createTable("table1")
                        .column("column1", "INTEGER")
                        .column("column2", "REAL")
                        .column("column3", "TEXT")
                        .primaryKey("column1")
                        .primaryKey("column2")
                        .build()
        );

        assertThrows(
                Exception.class,
                () -> Queries.createTableQueryInstance()
                        .createTable("table1")
                        .column("column1", "INTEGER")
                        .column("column2", "REAL")
                        .column("column3", "TEXT")
                        .primaryKey("column1")
                        .column("column1", "INTEGER")
                        .createTable("table2")
                        .build()
        );

        assertThrows(
                Exception.class,
                () -> Queries.createTableQueryInstance()
                        .createTable("table1")
                        .column("column1", "INTEGER")
                        .column("column2", "REAL")
                        .column("column3", "TEXT")
                        .primaryKey("column1")
                        .column("column4", "INTEGER")
                        .primaryKey("column4")
                        .build()
        );

        assertThrows(
                Exception.class,
                () -> Queries.createTableQueryInstance()
                        .createTable("table1")
                        .column("column1", "INTEGER")
                        .column("column2", "REAL")
                        .column("column3", "TEXT")
                        .primaryKey("column1")
                        .column("column4", "INTEGER")
                        .primaryKey("column4")
                        .primaryKey("column1")
                        .build()
        );
    }

    @Test
    public void shouldBeNull() {
        assertNull(Queries.createTableQueryInstance()
                .createTable("")
                .column("column1", "FAKE TYPE 1")
                .column("column2", "INTEGER")
                .column("column3", "FAKE TYPE 2")
                .primaryKey("column1")
                .build());

        assertNull(Queries.createTableQueryInstance()
                .createTable("table1")
                .column("column1", "INTEGER")
                .column("column2", "REAL")
                .build());

        assertNull(Queries.createTableQueryInstance()
                .createTable("table1")
                .build());

        assertNull(Queries.createTableQueryInstance()
                .build());

    }


}
