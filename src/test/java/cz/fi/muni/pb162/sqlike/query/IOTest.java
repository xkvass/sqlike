package cz.fi.muni.pb162.sqlike.query;

import cz.fi.muni.pb162.sqlike.data.database.Database;
import cz.fi.muni.pb162.sqlike.execute.Databases;
import cz.fi.muni.pb162.sqlike.execute.Queries;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static cz.fi.muni.pb162.sqlike.query.Dsl.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class IOTest {

    public static final Query INSERT_INTO_EMPLOYEE_EXPORT =
            Queries.insertQueryInstance()
                    .insertInto("employee", "id", "name", "salary", "department_id")
                    .values(1, "John", null, 1)
                    .values(2, null, 2000.5, 1)
                    .values(3, "Bob", 3000.5, 1)
                    .values(4, null, 1500.0, 2)
                    .values(5, "Jill", 2500.0, 2)
                    .values(6, "Joe", 3000.0, 3)
                    .values(7, "Judy", null, 3)
                    .values(8, "Jim", 4000.0, 4)
                    .values(9, null, 4500.0, 4)
                    .build();


    private static final String SELECT_ALL_FILE = "employee.csv";

    private static final Query SELECT_ALL_EXPORT =
            Queries.selectQueryInstance()
                    .selectFrom(table("employee"))
                    .orderBy(ord("id").asc())
                    .exportToCsv(SELECT_ALL_FILE, ",", true)
                    .build();

    private static final String SELECT_ALL_EXPORT_RESULT = """
            id,name,salary,department_id
            1,John,,1
            2,,2000.5,1
            3,Bob,3000.5,1
            4,,1500.0,2
            5,Jill,2500.0,2
            6,Joe,3000.0,3
            7,Judy,,3
            8,Jim,4000.0,4
            9,,4500.0,4""";

    private static final String SELECT_ID_NAME_EXPORT_FILE = "employee_id_name.csv";

    private static final Query SELECT_ID_NAME_EXPORT =
            Queries.selectQueryInstance()
                    .select(col("id"), col("name").as("employee_name"))
                    .from(table("employee"))
                    .orderBy(ord("id").asc())
                    .exportToCsv(SELECT_ID_NAME_EXPORT_FILE, ";", false, "US-ASCII")
                    .build();

    private static final String SELECT_ID_NAME_EXPORT_RESULT = """
            1;John
            2;
            3;Bob
            4;
            5;Jill
            6;Joe
            7;Judy
            8;Jim
            9;""";


    @Test
    public void exportSelectAll() throws IOException {
        final Database db = Databases.instance("workplace");

        db.execute(TestQueries.CREATE_TABLE_EMPLOYEE);
        db.execute(INSERT_INTO_EMPLOYEE_EXPORT);
        db.execute(SELECT_ALL_EXPORT);

        assertEquals(SELECT_ALL_EXPORT_RESULT, getFileContentAsString(SELECT_ALL_FILE));
    }

    @Test
    public void exportSelectIdName() throws IOException {
        final Database db = Databases.instance("workplace");

        db.execute(TestQueries.CREATE_TABLE_EMPLOYEE);
        db.execute(INSERT_INTO_EMPLOYEE_EXPORT);
        db.execute(SELECT_ID_NAME_EXPORT);

        assertEquals(SELECT_ID_NAME_EXPORT_RESULT, getFileContentAsString(SELECT_ID_NAME_EXPORT_FILE));
    }

    private String getFileContentAsString(String filePath) throws IOException {
        return Files.readString(Path.of(filePath));
    }


}
