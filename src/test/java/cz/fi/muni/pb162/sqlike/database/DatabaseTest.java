package cz.fi.muni.pb162.sqlike.database;

import cz.fi.muni.pb162.sqlike.data.database.Database;
import cz.fi.muni.pb162.sqlike.execute.Databases;
import cz.fi.muni.pb162.sqlike.query.TestQueries;
import cz.fi.muni.pb162.sqlike.query.result.QueryResult;
import cz.fi.muni.pb162.sqlike.query.result.QueryResultStatus;
import cz.fi.muni.pb162.sqlike.query.result.ResultRow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTest {

    private Database database = Databases.instance("workplace");


    private static final Set<String> employeeNames = Set.of(
            "John",
            "Jane",
            "Bob",
            "Jack",
            "Jill",
            "Joe",
            "Judy",
            "Jim",
            "Jake"
    );

    private static final Set<String> departmentNames = Set.of(
            "HR",
            "IT",
            "Finance",
            "Marketing"
    );

    @BeforeEach
    public void removeDatabase() {
        database = Databases.instance("workplace");
    }

    @Test
    public void employeeFlow_success() {
        // Create database
        assertEquals("workplace", database.getName());

        // Create table
        QueryResult createResult = database.execute(TestQueries.CREATE_TABLE_EMPLOYEE);
        assertEquals(QueryResultStatus.OK, createResult.status());

        // Insert two employees
        QueryResult insertResult = database.execute(TestQueries.INSERT_INTO_EMPLOYEE);
        assertEquals(QueryResultStatus.OK, insertResult.status());
        assertEquals(9, insertResult.rowsAffected());

        // Select all
        QueryResult selectResult = database.execute(TestQueries.SELECT_ALL);
        assertEquals(QueryResultStatus.OK, selectResult.status());
        assertTrue(selectResult.payload().isPresent());

        List<ResultRow> payload1 = new ArrayList<>();
        selectResult.payload().orElse(null).iterator().forEachRemaining(payload1::add);

        assertEquals(9, payload1.size());

        payload1.forEach(
                row -> {
                    assertTrue(row.getString("name").isPresent());
                    assertTrue(row.getDouble("salary").isPresent());
                    assertTrue(row.getInt("id").isPresent());

                    assertTrue(employeeNames.contains(row.getString("name").get()));
                    assertTrue(row.getDouble("salary").isPresent());
                    assertTrue(row.getInt("id").isPresent());
                }
        );


        // Select only names
        QueryResult selectNamesResult = database.execute(TestQueries.SELECT_NAMES);
        assertTrue(selectNamesResult.payload().isPresent());

        List<ResultRow> payload2 = new ArrayList<>();
        selectNamesResult.payload().orElse(null).iterator().forEachRemaining(payload2::add);

        assertEquals(QueryResultStatus.OK, selectNamesResult.status());
        assertEquals(9, payload2.size());

        // Drop table
        QueryResult dropResult = database.execute(TestQueries.DROP_TABLE_EMPLOYEE);
        assertEquals(QueryResultStatus.OK, dropResult.status());

        // Select all after drop
        QueryResult selectResultAfterDelete = database.execute(TestQueries.SELECT_ALL);
        assertEquals(QueryResultStatus.ERROR, selectResultAfterDelete.status());
    }

    @Test
    public void createTable_fail() {
        database.execute(TestQueries.CREATE_TABLE_EMPLOYEE);
        QueryResult createResultFail = database.execute(TestQueries.CREATE_TABLE_EMPLOYEE);
        assertEquals(QueryResultStatus.ERROR, createResultFail.status());

    }

    @Test
    public void insertDuplicateIds_fail() {
        //Prepare database
        QueryResult createResult = database.execute(TestQueries.CREATE_TABLE_EMPLOYEE);
        assertEquals(QueryResultStatus.OK, createResult.status());

        QueryResult insertResult = database.execute(TestQueries.INSERT_INTO_EMPLOYEE);
        assertEquals(QueryResultStatus.OK, insertResult.status());
        assertEquals(9, insertResult.rowsAffected());

        //Insert duplicate ids
        QueryResult insertResult2 = database.execute(TestQueries.INSERT_INTO_EMPLOYEE);
        assertEquals(QueryResultStatus.ERROR, insertResult2.status());
        assertEquals(0, insertResult2.rowsAffected());
    }

    @Test
    public void selectFromNonExistingTable_fail() {
        database = Databases.instance("workplace");
        QueryResult selectResult = database.execute(TestQueries.SELECT_ALL);
        assertEquals(QueryResultStatus.ERROR, selectResult.status());
    }

    @Test
    public void dropNonExistingTable_fail() {
        database = Databases.instance("workplace");
        QueryResult dropResult = database.execute(TestQueries.DROP_TABLE_EMPLOYEE);
        assertEquals(QueryResultStatus.ERROR, dropResult.status());
    }


    @Test
    public void insertSameSalary_success() {
        //Prepare database
        database = Databases.instance("workplace");
        QueryResult createResult = database.execute(TestQueries.CREATE_TABLE_EMPLOYEE);
        assertEquals(QueryResultStatus.OK, createResult.status());

        QueryResult insertResult = database.execute(TestQueries.INSERT_SAME_SALARY);
        assertEquals(QueryResultStatus.OK, insertResult.status());
        assertEquals(2, insertResult.rowsAffected());

        // Select all
        QueryResult selectResult = database.execute(TestQueries.SELECT_ALL);
        assertTrue(selectResult.payload().isPresent());
        List<ResultRow> payload = new ArrayList<>();
        selectResult.payload().orElse(null).iterator().forEachRemaining(payload::add);

        assertEquals(QueryResultStatus.OK, selectResult.status());
        assertEquals(2, payload.size());
        assertEquals((payload.get(0).getDouble("salary")), (payload.get(1).getDouble("salary")));
    }

    @Test
    public void testDatatabase() {
        Database database = Databases.instance("workplace");
        Database databaseEqual = Databases.instance("workplace");

        assertEquals("workplace", database.getName());
        assertEquals("workplace", databaseEqual.getName());
        assertEquals(database, databaseEqual);
    }

    @Test
    public void deleteEmployee_success() {
        database = Databases.instance("workplace");
        database.execute(TestQueries.CREATE_TABLE_EMPLOYEE);
        database.execute(TestQueries.INSERT_INTO_EMPLOYEE);

        QueryResult deleteResult = database.execute(TestQueries.DELETE_EMPLOYEE);
        assertEquals(QueryResultStatus.OK, deleteResult.status());
        assertEquals(1, deleteResult.rowsAffected());

        QueryResult selectResult = database.execute(TestQueries.SELECT_ALL);
        assertTrue(selectResult.toString().lines().noneMatch(line -> line.contains("John")));
    }

    @Test
    public void selectEmployeeSuccess() {
        database = Databases.instance("workplace");
        database.execute(TestQueries.CREATE_TABLE_EMPLOYEE);
        database.execute(TestQueries.INSERT_INTO_EMPLOYEE);

        QueryResult result = database.execute(TestQueries.SELECT_EMPLOYEE);
        assertEquals(QueryResultStatus.OK, result.status());
        assertTrue(result.payload().isPresent());

        List<ResultRow> rows = new ArrayList<>();
        result.payload().get().forEach(rows::add);
        assertEquals(9, rows.size());
        rows.forEach(row -> {
            assertTrue(row.getString("employee_name").isPresent());
            assertTrue(employeeNames.contains(row.getString("employee_name").get()));
        });
    }

    @Test
    public void selectEmployeeJoinWithWhere_success() {
        database = Databases.instance("workplace");
        database.execute(TestQueries.CREATE_TABLE_EMPLOYEE);
        database.execute(TestQueries.CREATE_TABLE_DEPARTMENT);
        database.execute(TestQueries.INSERT_INTO_EMPLOYEE);
        database.execute(TestQueries.INSERT_INTO_DEPARTMENT);

        QueryResult result = database.execute(TestQueries.SELECT_EMPLOYEE_WITH_WHERE);
        assertEquals(QueryResultStatus.OK, result.status());
        assertTrue(result.payload().isPresent());

        result.payload().get().forEach(row -> {
            assertNotNull(row.getString("employee_name").orElse(null));
        });
    }

    @Test
    public void selectWithWhereAndOrder_success() {
        database = Databases.instance("workplace");
        database.execute(TestQueries.CREATE_TABLE_EMPLOYEE);
        database.execute(TestQueries.INSERT_INTO_EMPLOYEE);

        QueryResult result = database.execute(TestQueries.SELECT_EMPLOYEE_WITH_WHERE_AND_ORDER);
        assertEquals(QueryResultStatus.OK, result.status());
        assertTrue(result.payload().isPresent());

        List<Double> salaries = new ArrayList<>();
        result.payload().get().forEach(row -> {
            assertTrue(row.getDouble("salary").isPresent());
            salaries.add(row.getDouble("salary").get());
        });

        for (int i = 1; i < salaries.size(); i++) {
            assertTrue(salaries.get(i - 1) >= salaries.get(i));
        }
    }

    @Test
    public void selectWithLimit_success() {
        database = Databases.instance("workplace");
        database.execute(TestQueries.CREATE_TABLE_EMPLOYEE);
        database.execute(TestQueries.INSERT_INTO_EMPLOYEE);

        QueryResult result = database.execute(TestQueries.SELECT_EMPLOYEE_WITH_WHERE_AND_ORDER_AND_LIMIT);
        assertEquals(QueryResultStatus.OK, result.status());
        assertTrue(result.payload().isPresent());

        List<ResultRow> rows = new ArrayList<>();
        result.payload().get().forEach(rows::add);
        assertEquals(1, rows.size());
    }

    @Test
    public void selectWithOffset_success() {
        database = Databases.instance("workplace");
        database.execute(TestQueries.CREATE_TABLE_EMPLOYEE);
        database.execute(TestQueries.INSERT_INTO_EMPLOYEE);

        QueryResult result = database.execute(TestQueries.SELECT_EMPLOYEE_WITH_WHERE_AND_ORDER_AND_LIMIT_OFFSET);
        assertEquals(QueryResultStatus.OK, result.status());
        assertTrue(result.payload().isPresent());

        List<ResultRow> rows = new ArrayList<>();
        result.payload().get().forEach(rows::add);
        assertEquals(2, rows.size());

        assertTrue(rows.get(0).getString("name").isPresent());
        assertTrue(rows.get(1).getString("name").isPresent());
        assertEquals("Jim", rows.get(0).getString("name").get());
        assertEquals("Judy", rows.get(1).getString("name").get());

    }

    @Test
    public void selectWithGroupByAndHaving_success() {
        database = Databases.instance("workplace");
        database.execute(TestQueries.CREATE_TABLE_EMPLOYEE);
        database.execute(TestQueries.INSERT_INTO_EMPLOYEE);

        QueryResult result = database.execute(TestQueries.SELECT_EMPLOYEE_WITH_WHERE_AND_ORDER_AND_GROUP_BY_HAVING);
        assertEquals(QueryResultStatus.OK, result.status());
        assertTrue(result.payload().isPresent());
        List<ResultRow> rows = new ArrayList<>();
        result.payload().get().forEach(rows::add);
        assertEquals(1, rows.size());
        rows.forEach(row -> {
            assertTrue(row.getInt("department").isPresent());
            assertEquals(4, row.getInt("department").get());
        });
    }

    @Test
    public void selectAggregate_success() {
        database = Databases.instance("workplace");

        // Prepare data
        database.execute(TestQueries.CREATE_TABLE_EMPLOYEE);
        database.execute(TestQueries.INSERT_INTO_EMPLOYEE);

        // Execute aggregation
        QueryResult result = database.execute(TestQueries.SELECT_EMPLOYEE_AGGREGATE);
        assertEquals(QueryResultStatus.OK, result.status());
        assertTrue(result.payload().isPresent());

        List<ResultRow> rows = new ArrayList<>();
        result.payload().get().iterator().forEachRemaining(rows::add);

        assertEquals(4, rows.size());

        Map<Object, ResultRow> byDepartmentId = rows.stream()
                .collect(Collectors.toMap(
                        r -> r.getInt("department_id").get(),
                        Function.identity()
                ));

        ResultRow dept1 = byDepartmentId.get(1);
        assertEquals(Math.round(6001.0), Math.round(dept1.getDouble("total_salary").get()));
        assertEquals(3, dept1.getInt("count").get());
        assertEquals(Math.round(2000.33), Math.round(dept1.getDouble("avg").get()));
        assertEquals(1000.0, dept1.getDouble("min").get());
        assertEquals(3000.5, dept1.getDouble("max").get());
    }

    @Test
    public void updateEmployee_success() {
        database = Databases.instance("workplace");
        database.execute(TestQueries.CREATE_TABLE_EMPLOYEE);
        database.execute(TestQueries.INSERT_INTO_EMPLOYEE);

        QueryResult result = database.execute(TestQueries.UPDATE_EMPLOYEE);
        assertEquals(QueryResultStatus.OK, result.status());
        assertEquals(1, result.rowsAffected());

        QueryResult select = database.execute(TestQueries.SELECT_ALL);
        List<ResultRow> rows = new ArrayList<>();
        select.payload().get().iterator().forEachRemaining(rows::add);

        rows.stream()
                .filter(row -> row.getString("name").equals("John"))
                .forEach(row -> assertEquals(2000.0, (row.getDouble("salary")).get()));
    }

    @Test
    public void alterTableAddColumn_success() {
        database = Databases.instance("workplace");
        database.execute(TestQueries.CREATE_TABLE_EMPLOYEE);
        database.execute(TestQueries.INSERT_INTO_TWO_EMPLOYEES);

        QueryResult result = database.execute(TestQueries.ALTER_TABLE_EMPLOYEE_ADD_COLUMN);
        assertEquals(QueryResultStatus.OK, result.status());

        QueryResult updateResult = database.execute(TestQueries.UPDATE_SET_AGE);
        assertEquals(QueryResultStatus.OK, updateResult.status());
        assertEquals(1, updateResult.rowsAffected());

        QueryResult select = database.execute(TestQueries.SELECT_ALL);
        assertEquals(QueryResultStatus.OK, select.status());
        assertTrue(select.payload().isPresent());
        List<ResultRow> rows = new ArrayList<>();
        select.payload().orElse(null).iterator().forEachRemaining(rows::add);
        assertEquals(2, rows.size());
        assertEquals(30, rows.stream().filter(row -> row.getString("name").get().equals("John")).findFirst().get().getInt("age").get());

    }

    @Test
    public void alterTableRename_success() {
        database = Databases.instance("workplace");
        database.execute(TestQueries.CREATE_TABLE_EMPLOYEE);
        database.execute(TestQueries.INSERT_INTO_TWO_EMPLOYEES);

        QueryResult result = database.execute(TestQueries.ALTER_TABLE_EMPLOYEE_RENAME);
        assertEquals(QueryResultStatus.OK, result.status());
        QueryResult selectResult = database.execute(TestQueries.SELECT_ALL_RENAMED);
        assertEquals(QueryResultStatus.OK, selectResult.status());
        assertTrue(selectResult.payload().isPresent());
        List<ResultRow> rows = new ArrayList<>();
        selectResult.payload().orElse(null).iterator().forEachRemaining(rows::add);
        assertEquals(2, rows.size());
    }

    @Test
    public void alterTableDropColumn_success() {
        database = Databases.instance("workplace");
        database.execute(TestQueries.CREATE_TABLE_EMPLOYEE);
        database.execute(TestQueries.INSERT_INTO_TWO_EMPLOYEES);

        QueryResult result = database.execute(TestQueries.ALTER_TABLE_EMPLOYEE_DROP_COLUMN);
        assertEquals(QueryResultStatus.OK, result.status());

        QueryResult selectResult = database.execute(TestQueries.SELECT_ALL);
        assertEquals(QueryResultStatus.OK, selectResult.status());
        assertTrue(selectResult.payload().isPresent());
        List<ResultRow> rows = new ArrayList<>();
        selectResult.payload().orElse(null).iterator().forEachRemaining(rows::add);
        assertEquals(2, rows.size());
        rows.forEach(row -> {
            assertEquals(Optional.empty(), row.getString("name"));
        });
    }
}
