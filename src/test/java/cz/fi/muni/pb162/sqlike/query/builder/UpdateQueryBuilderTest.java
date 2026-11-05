package cz.fi.muni.pb162.sqlike.query.builder;

import cz.fi.muni.pb162.sqlike.execute.Queries;
import org.junit.jupiter.api.Test;

import static cz.fi.muni.pb162.sqlike.query.Dsl.*;
import static org.junit.jupiter.api.Assertions.*;

public class UpdateQueryBuilderTest implements QueryBuilderTest {

    @Test
    public void shouldBuildQuery() {
        assertNotNull(Queries.updateQueryInstance()
                .update(table("table1"))
                .set(col("name"), "Alice")
                .where(eq(col("id"), 1))
                .build());

        assertNotNull(Queries.updateQueryInstance()
                .update(table("table1"))
                .set(col("score"), 100)
                .build());

        assertNotNull(Queries.updateQueryInstance()
                .update(table("table1"))
                .set(col("a"), 1)
                .set(col("b"), 2)
                .set(col("c"), 3)
                .where(gt(col("score"), 100))
                .build());
    }

    @Test
    public void shouldThrowException() {
        assertThrows(Exception.class, () ->
                Queries.updateQueryInstance()
                        .update(table("t1"))
                        .update(table("t2"))
                        .set(col("x"), 10)
                        .build()
        );
    }

    @Test
    public void shouldBeNull() {
        assertNull(Queries.updateQueryInstance().build());

        assertNull(Queries.updateQueryInstance()
                .update(table("t"))
                .build()
        );

        assertNull(Queries.updateQueryInstance()
                .set(col("x"), 10)
                .build()
        );

        assertNull(Queries.updateQueryInstance()
                .where(eq(col("x"), 1))
                .build());

        assertNull(Queries.updateQueryInstance()
                .update(table("t"))
                .where(eq(col("x"), 1))
                .build());
    }
}
