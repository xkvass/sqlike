package cz.fi.muni.pb162.sqlike.query.builder;

import cz.fi.muni.pb162.sqlike.execute.Queries;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InsertQueryBuilderTest implements QueryBuilderTest {


    @Test
    public void shouldBuildQuery() {
        assertNotNull(Queries.insertQueryInstance()
                .insertInto("table1", "column1", "column2", "column3")
                .values("value1", "value2", "value3")
                .values("value4", "value5", "value6")
                .values("value7", "value8", "value9")
                .build());
    }

    @Test
    public void shouldThrowException() {
        assertThrows(
                Exception.class,
                () -> Queries.insertQueryInstance()
                        .insertInto("table1", "column1", "column2", "column3")
                        .insertInto("table2", "column1", "column2", "column3")
                        .values("column1", "value1")
                        .values("column2", "value2")
                        .values("column3", "value3")
                        .values("column4", "value4")
                        .build()
        );
    }

    @Test
    public void shouldBeNull() {
        assertNull(Queries.insertQueryInstance()
                .build());

        assertNull(Queries.insertQueryInstance()
                .insertInto("table1", "column1", "column2", "column3")
                .build());

        assertNull(Queries.insertQueryInstance()
                .values("column1", "value1")
                .build());
    }

}
