package cz.fi.muni.pb162.sqlike.query;

/**
 * Represents a query that can be executed on a database.
 * <p>
 * Queries are considered to be same if they have the same SQL string representation.
 */
public interface Query {

    /**
     * Returns the SQL string representation of the query.
     * <p>
     * Query should be able to convert to the SQL query string. You do not need to consider new lines or
     * multiple spaces in the string representation (There should be no new lines and just one space between each word).
     * Should end with a semicolon.
     * <p>
     * Create table query example:
     * <pre>
     *     CREATE TABLE employees (id INTEGER PRIMARY KEY, name TEXT, age INTEGER, department TEXT, salary INTEGER);
     * </pre>
     * Insert query example:
     * <pre>
     *     INSERT INTO employees (name, age, department, salary) VALUES ('Alice Johnson', 30, 'HR', 50000);
     * </pre>
     * Select query example:
     * <pre>
     *     SELECT * FROM employees;
     * </pre>
     * or
     * <pre>
     *     SELECT name, age FROM employees;
     * </pre>
     * Drop table query example:
     * <pre>
     *     DROP TABLE employees;
     * </pre>
     * Update query example:
     * <pre>
     *     UPDATE employees SET salary = 55000 WHERE name = 'Alice Johnson';
     * </pre>
     * Delete query example:
     * <pre>
     *     DELETE FROM employees WHERE name = 'Alice Johnson';
     * </pre>
     * Create index query example:
     * <pre>
     *    CREATE INDEX idx_name ON employees (name);
     * </pre>
     * Alter table query example:
     * <pre>
     *    ALTER TABLE employees ADD COLUMN address TEXT;
     * </pre>
     * Join table example
     * <pre>
     *     SELECT e.name AS employee_name, d.name AS department_name FROM employees AS e JOIN departments AS d ON e.department_id = d.id WHERE e.salary > 50000 ORDER BY e.salary DESC LIMIT 10 OFFSET 5;
     * </pre>
     * Aggregate function example
     * <pre>
     *     SELECT SUM(salary) AS total_salary, COUNT(*) AS count, AVG(salary) AS avg, MIN(salary) AS min, MAX(salary) AS max FROM employees GROUP BY department_id HAVING AVG(salary) > 50000;
     * </pre>
     * <p>
     * You can find more examples in tests
     * <p>
     *
     * @return SQL string representation of the query
     */
    String toSqlString();

    /**
     * Returns the type of the query.
     *
     * @return type of the query
     */
    QueryType getQueryType();

}
