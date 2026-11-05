package cz.fi.muni.pb162.sqlike.query.builder;

import cz.fi.muni.pb162.sqlike.execute.Queries;
import org.junit.jupiter.api.Test;

import static cz.fi.muni.pb162.sqlike.query.Dsl.*;
import static org.junit.jupiter.api.Assertions.*;

public class DeleteQueryBuilderTest implements QueryBuilderTest {

    @Test
    public void shouldBuildQuery() {
        assertNotNull(Queries.deleteQueryInstance()
                .deleteFrom(table("table1"))
                .where(gt(col("c1"), col("c2")))
                .build());

        assertNotNull(Queries.deleteQueryInstance()
                .deleteFrom(table("table1"))
                .build());
    }

    @Test
    public void shouldThrowException() {
        assertThrows(Exception.class, () ->
                Queries.deleteQueryInstance()
                        .deleteFrom(table("table1"))
                        .deleteFrom(table("other_table"))
                        .build()
        );
    }

    @Test
    public void shouldBeNull() {
        assertNull(Queries.deleteQueryInstance().build());

        assertNull(Queries.deleteQueryInstance()
                .where(lt(col("age"), 42))
                .build());
    }
}
