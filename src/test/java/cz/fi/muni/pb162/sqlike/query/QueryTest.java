package cz.fi.muni.pb162.sqlike.query;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryTest {

    @Test
    public void additionalQueriesToSql() {
        assertEquals("CREATE_TABLE", TestQueries.CREATE_TABLE_EMPLOYEE.getQueryType().getQueryTypeName());
        assertEquals("CREATE TABLE employee (id INTEGER PRIMARY KEY, name TEXT, salary REAL, department_id INTEGER);", TestQueries.CREATE_TABLE_EMPLOYEE.toSqlString());

        assertEquals("INSERT", TestQueries.INSERT_INTO_EMPLOYEE.getQueryType().getQueryTypeName());
        assertEquals("INSERT INTO employee (id, name, salary, department_id) VALUES (1, 'John', 1000.0, 1), (2, 'Jane', 2000.5, 1), (3, 'Bob', 3000.5, 1), (4, 'Jack', 1500.0, 2), (5, 'Jill', 2500.0, 2), (6, 'Joe', 3000.0, 3), (7, 'Judy', 3500.0, 3), (8, 'Jim', 4000.0, 4), (9, 'Jake', 4500.0, 4);", TestQueries.INSERT_INTO_EMPLOYEE.toSqlString());

        assertEquals("SELECT", TestQueries.SELECT_NAMES.getQueryType().getQueryTypeName());
        assertEquals("SELECT name AS n FROM employee;", TestQueries.SELECT_NAMES.toSqlString());

        assertEquals("SELECT", TestQueries.SELECT_ALL.getQueryType().getQueryTypeName());
        assertEquals("SELECT * FROM employee;", TestQueries.SELECT_ALL.toSqlString());

        assertEquals("DROP_TABLE", TestQueries.DROP_TABLE_EMPLOYEE.getQueryType().getQueryTypeName());
        assertEquals("DROP TABLE employee;", TestQueries.DROP_TABLE_EMPLOYEE.toSqlString());

        assertEquals("CREATE_TABLE", TestQueries.CREATE_TABLE_DEPARTMENT.getQueryType().getQueryTypeName());
        assertEquals("CREATE TABLE department (id INTEGER PRIMARY KEY, name TEXT);", TestQueries.CREATE_TABLE_DEPARTMENT.toSqlString());

        assertEquals("INSERT", TestQueries.INSERT_INTO_DEPARTMENT.getQueryType().getQueryTypeName());
        assertEquals("INSERT INTO department (id, name) VALUES (1, 'HR'), (2, 'IT'), (3, 'Finance'), (4, 'Marketing');", TestQueries.INSERT_INTO_DEPARTMENT.toSqlString());

        assertEquals("INSERT", TestQueries.INSERT_SAME_SALARY.getQueryType().getQueryTypeName());
        assertEquals("INSERT INTO employee (id, name, salary) VALUES (1, 'John', 1000.0), (2, 'Jane', 1000.0);", TestQueries.INSERT_SAME_SALARY.toSqlString());

        assertEquals("SELECT", TestQueries.SELECT_ALL_RENAMED.getQueryType().getQueryTypeName());
        assertEquals("SELECT * FROM employee_renamed;", TestQueries.SELECT_ALL_RENAMED.toSqlString());

        assertEquals("CREATE_INDEX", TestQueries.CREATE_INDEX_EMPLOYEE_NAME.getQueryType().getQueryTypeName());
        assertEquals("CREATE INDEX employee_name_index ON employee (name ASC);", TestQueries.CREATE_INDEX_EMPLOYEE_NAME.toSqlString());

        assertEquals("DELETE", TestQueries.DELETE_EMPLOYEE.getQueryType().getQueryTypeName());
        assertEquals("DELETE FROM employee WHERE name = 'John' AND salary > 900.0;", TestQueries.DELETE_EMPLOYEE.toSqlString());

        assertEquals("SELECT", TestQueries.SELECT_EMPLOYEE.getQueryType().getQueryTypeName());
        assertEquals("SELECT name AS employee_name FROM employee;", TestQueries.SELECT_EMPLOYEE.toSqlString());

        assertEquals("SELECT", TestQueries.SELECT_EMPLOYEE_WITH_WHERE.getQueryType().getQueryTypeName());
        assertEquals("SELECT name AS employee_name FROM employee WHERE salary > 1000.0;", TestQueries.SELECT_EMPLOYEE_WITH_WHERE.toSqlString());

        assertEquals("SELECT", TestQueries.SELECT_EMPLOYEE_WITH_WHERE_AND_ORDER.getQueryType().getQueryTypeName());
        assertEquals("SELECT name, salary FROM employee WHERE salary > 1000.0 ORDER BY salary DESC;", TestQueries.SELECT_EMPLOYEE_WITH_WHERE_AND_ORDER.toSqlString());

        assertEquals("SELECT", TestQueries.SELECT_EMPLOYEE_WITH_WHERE_AND_ORDER_AND_LIMIT.getQueryType().getQueryTypeName());
        assertEquals("SELECT name FROM employee WHERE salary > 1000.0 ORDER BY salary DESC LIMIT 1;", TestQueries.SELECT_EMPLOYEE_WITH_WHERE_AND_ORDER_AND_LIMIT.toSqlString());

        assertEquals("SELECT", TestQueries.SELECT_EMPLOYEE_WITH_WHERE_AND_ORDER_AND_LIMIT_OFFSET.getQueryType().getQueryTypeName());
        assertEquals("SELECT name FROM employee WHERE salary > 1000.0 ORDER BY salary DESC LIMIT 2 OFFSET 1;", TestQueries.SELECT_EMPLOYEE_WITH_WHERE_AND_ORDER_AND_LIMIT_OFFSET.toSqlString());

        assertEquals("SELECT", TestQueries.SELECT_EMPLOYEE_WITH_WHERE_AND_ORDER_AND_GROUP_BY_HAVING.getQueryType().getQueryTypeName());
        assertEquals("SELECT department_id AS department FROM employee WHERE salary > 1000.0 GROUP BY department_id HAVING AVG(salary) > 4000.0 ORDER BY salary DESC;", TestQueries.SELECT_EMPLOYEE_WITH_WHERE_AND_ORDER_AND_GROUP_BY_HAVING.toSqlString());

        assertEquals("SELECT", TestQueries.SELECT_EMPLOYEE_AGGREGATE.getQueryType().getQueryTypeName());
        assertEquals("SELECT department_id, SUM(salary) AS total_salary, COUNT(name) AS count, AVG(salary) AS avg, MIN(salary) AS min, MAX(salary) AS max FROM employee GROUP BY department_id;", TestQueries.SELECT_EMPLOYEE_AGGREGATE.toSqlString());

        assertEquals("SELECT", TestQueries.SELECT_EMPLOYEE_AGGREGATE_HAVING.getQueryType().getQueryTypeName());
        assertEquals("SELECT SUM(salary) AS total_salary, COUNT(*) AS count, AVG(salary) AS avg, MIN(salary) AS min, MAX(salary) AS max FROM employee GROUP BY department_id HAVING salary > 1000.0 AND count > 2;", TestQueries.SELECT_EMPLOYEE_AGGREGATE_HAVING.toSqlString());

        assertEquals("UPDATE", TestQueries.UPDATE_EMPLOYEE.getQueryType().getQueryTypeName());
        assertEquals("UPDATE employee SET salary = 2000.0 WHERE name = 'John';", TestQueries.UPDATE_EMPLOYEE.toSqlString());

        assertEquals("ALTER_TABLE_ADD_COLUMN", TestQueries.ALTER_TABLE_EMPLOYEE_ADD_COLUMN.getQueryType().getQueryTypeName());
        assertEquals("ALTER TABLE employee ADD COLUMN age INTEGER;", TestQueries.ALTER_TABLE_EMPLOYEE_ADD_COLUMN.toSqlString());

        assertEquals("ALTER_TABLE_RENAME_TABLE", TestQueries.ALTER_TABLE_EMPLOYEE_RENAME.getQueryType().getQueryTypeName());
        assertEquals("ALTER TABLE employee RENAME TO employee_renamed;", TestQueries.ALTER_TABLE_EMPLOYEE_RENAME.toSqlString());

        assertEquals("ALTER_TABLE_DROP_COLUMN", TestQueries.ALTER_TABLE_EMPLOYEE_DROP_COLUMN.getQueryType().getQueryTypeName());
        assertEquals("ALTER TABLE employee DROP COLUMN name;", TestQueries.ALTER_TABLE_EMPLOYEE_DROP_COLUMN.toSqlString());

        assertEquals("SELECT", TestQueries.SELECT_TABLE_WHERE_JOHN.getQueryType().getQueryTypeName());
        assertEquals("SELECT name AS n FROM employee WHERE name = 'John';", TestQueries.SELECT_TABLE_WHERE_JOHN.toSqlString());

        assertEquals("SELECT", TestQueries.SELECT_TABLE_WHERE_JANE.getQueryType().getQueryTypeName());
        assertEquals("SELECT name FROM employee WHERE name = 'Jane';", TestQueries.SELECT_TABLE_WHERE_JANE.toSqlString());

        assertEquals("INSERT", TestQueries.INSERT_INTO_TWO_EMPLOYEES.getQueryType().getQueryTypeName());
        assertEquals("INSERT INTO employee (id, name, salary, department_id) VALUES (1, 'John', 1000.0, 1), (2, 'Jane', 2000.5, 2);", TestQueries.INSERT_INTO_TWO_EMPLOYEES.toSqlString());
    }


}
