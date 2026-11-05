package cz.fi.muni.pb162.sqlike.query.builder;

import cz.fi.muni.pb162.sqlike.execute.Queries;
import org.junit.jupiter.api.Test;

import static cz.fi.muni.pb162.sqlike.query.Dsl.*;
import static org.junit.jupiter.api.Assertions.*;

public class SelectQueryBuilderTest implements QueryBuilderTest {

    @Test
    public void shouldBuildQuery() {
        assertNotNull(
                Queries.selectQueryInstance()
                        .select(col("column1"))
                        .from(table("employee"))
                        .build()
        );

        assertNotNull(
                Queries.selectQueryInstance()
                        .selectFrom(table("employee"))
                        .build()
        );

        assertNotNull(
                Queries.selectQueryInstance()
                        .select(col("id"), col("name"))
                        .from(table("employee"))
                        .where(gt(col("age"), 25))
                        .groupBy(col("department"))
                        .having(gt(col("salary"), 500000))
                        .orderBy(ord("name"))
                        .limit(10)
                        .offset(5)
                        .build()
        );

        assertNotNull(
                Queries.selectQueryInstance()
                        .select(col("a"))
                        .from(table("t"))
                        .groupBy(col("a"))
                        .having(eq(col("a"), col("b")))
                        .build()
        );
    }

    @Test
    public void shouldThrowException() {
        assertThrows(
                Exception.class,
                () -> Queries.selectQueryInstance()
                        .select(col("column1"))
                        .select(col("column2"))
                        .from(table("employee"))
                        .build()
        );

        assertThrows(
                Exception.class,
                () -> Queries.selectQueryInstance()
                        .select(col("column1"))
                        .from(table("employee"))
                        .from(table("employee"))
                        .build()
        );

        assertThrows(
                Exception.class,
                () -> Queries.selectQueryInstance()
                        .select(col("column1"))
                        .from(table("employee"))
                        .select(col("column2"))
                        .build()
        );

        assertThrows(
                Exception.class,
                () -> Queries.selectQueryInstance()
                        .selectFrom(table("employee"))
                        .selectFrom(table("employee"))
                        .build()
        );


        assertThrows(
                Exception.class,
                () -> Queries.selectQueryInstance()
                        .select(col("a"))
                        .from(table("t"))
                        .limit(10)
                        .limit(5)  // limit twice
                        .build()
        );

        assertThrows(
                Exception.class,
                () -> Queries.selectQueryInstance()
                        .select(col("a"))
                        .from(table("t"))
                        .offset(1)
                        .offset(3)  // offset twice
                        .build()
        );
    }

    @Test
    public void shouldBeNull() {
        assertNull(
                Queries.selectQueryInstance()
                        .build()
        );

        assertNull(
                Queries.selectQueryInstance()
                        .select(col("column1"))
                        .build()
        );

        assertNull(
                Queries.selectQueryInstance()
                        .from(table("employee"))
                        .build()
        );
        
    }
}
