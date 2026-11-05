package cz.fi.muni.pb162.sqlike.query.builder;

import cz.fi.muni.pb162.sqlike.execute.Queries;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DropTableQueryBuilderTest implements QueryBuilderTest {

    @Test
    public void shouldBuildQuery() {
        assertNotNull(Queries.dropTableQueryInstance()
                .dropTable("table1")
                .build());

    }

    @Test
    public void shouldThrowException() {
        assertThrows(
                Exception.class,
                () -> Queries.dropTableQueryInstance()
                        .dropTable("table1")
                        .dropTable("table2")
                        .build()
        );
    }

    @Test
    public void shouldBeNull() {
        assertNull(Queries.dropTableQueryInstance()
                .build());

        assertNull(Queries.dropTableQueryInstance()
                .dropTable("")
                .build());
    }

}