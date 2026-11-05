package cz.fi.muni.pb162.sqlike.query.builder;

import cz.fi.muni.pb162.sqlike.execute.Queries;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlterTableQueryBuilderTest implements QueryBuilderTest {

    @Test
    public void shouldBuildQuery() {
        assertNotNull(Queries.alterTableQueryInstance()
                .alterTable("table1")
                .addColumn("new_column", "TEXT")
                .build());

        assertNotNull(Queries.alterTableQueryInstance()
                .alterTable("table2")
                .renameTo("renamed_table")
                .build());

        assertNotNull(Queries.alterTableQueryInstance()
                .alterTable("table3")
                .dropColumn("old_column")
                .build());
    }

    @Test
    public void shouldThrowException() {
        assertThrows(
                Exception.class,
                () -> Queries.alterTableQueryInstance()
                        .alterTable("table1")
                        .addColumn("col1", "INTEGER")
                        .alterTable("table2")
                        .build()
        );

        assertThrows(
                Exception.class,
                () -> Queries.alterTableQueryInstance()
                        .alterTable("table1")
                        .renameTo("temp_table")
                        .renameTo("another_table")
                        .build()
        );
//
//        assertThrows(
//                Exception.class,
//                () -> Queries.alterTableQueryInstance()
//                        .renameTo("new_table")
//                        .build()
//        );
    }

    @Test
    public void shouldBeNull() {
        assertNull(
                Queries.alterTableQueryInstance()
                        .renameTo("new_table")
                        .build()
        );

        assertNull(Queries.alterTableQueryInstance()
                .alterTable("table1")
                .build());

        assertNull(Queries.alterTableQueryInstance()
                .build());
    }
}
