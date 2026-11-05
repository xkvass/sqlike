package cz.fi.muni.pb162.sqlike.query.builder;

import cz.fi.muni.pb162.sqlike.execute.Queries;
import org.junit.jupiter.api.Test;

import static cz.fi.muni.pb162.sqlike.query.Dsl.ord;
import static org.junit.jupiter.api.Assertions.*;

public class CreateIndexQueryBuilderTest implements QueryBuilderTest {

    @Test
    public void shouldBuildQuery() {
        assertNotNull(Queries.createIndexQueryInstance()
                .createIndex("index1")
                .on("table1", ord("column1"))
                .build());

        assertNotNull(Queries.createIndexQueryInstance()
                .createIndex("index2")
                .on("table2", ord("column1"))
                .build());
    }

    @Test
    public void shouldThrowException() {
        assertThrows(Exception.class, () ->
                Queries.createIndexQueryInstance()
                        .createIndex("index1")
                        .on("table1", ord("column1"))
                        .createIndex("index2")
                        .build()
        );

        assertThrows(Exception.class, () ->
                Queries.createIndexQueryInstance()
                        .createIndex("index1")
                        .on("table1", ord("column1"))
                        .on("table2", ord("column1"))
                        .build()
        );

    }

    @Test
    public void shouldBeNull() {
        assertNull(
                Queries.createIndexQueryInstance()
                        .on("table1", ord("column1"))
                        .build()
        );

        assertNull(Queries.createIndexQueryInstance()
                .createIndex("index1")
                .build());

        assertNull(Queries.createIndexQueryInstance()
                .on("table1", ord("column1"))
                .build());

        assertNull(Queries.createIndexQueryInstance()
                .build());
    }
}
