package cz.fi.muni.pb162.sqlike.query;

import cz.fi.muni.pb162.sqlike.execute.Queries;

import static cz.fi.muni.pb162.sqlike.query.Dsl.*;

public class TestQueries {

    public static final Query CREATE_TABLE_EMPLOYEE =
            Queries.createTableQueryInstance()
                    .createTable("employee")
                    .column("id", "INTEGER")
                    .primaryKey("id")
                    .column("name", "TEXT")
                    .column("salary", "REAL")
                    .column("department_id", "INTEGER")
                    .build();

    public static final Query CREATE_TABLE_DEPARTMENT =
            Queries.createTableQueryInstance()
                    .createTable("department")
                    .column("id", "INTEGER")
                    .primaryKey("id")
                    .column("name", "TEXT")
                    .build();

    public static final Query INSERT_INTO_DEPARTMENT =
            Queries.insertQueryInstance()
                    .insertInto("department", "id", "name")
                    .values(1, "HR")
                    .values(2, "IT")
                    .values(3, "Finance")
                    .values(4, "Marketing")
                    .build();

    public static final Query INSERT_INTO_EMPLOYEE =
            Queries.insertQueryInstance()
                    .insertInto("employee", "id", "name", "salary", "department_id")
                    .values(1, "John", 1000.0, 1)
                    .values(2, "Jane", 2000.5, 1)
                    .values(3, "Bob", 3000.5, 1)
                    .values(4, "Jack", 1500.0, 2)
                    .values(5, "Jill", 2500.0, 2)
                    .values(6, "Joe", 3000.0, 3)
                    .values(7, "Judy", 3500.0, 3)
                    .values(8, "Jim", 4000.0, 4)
                    .values(9, "Jake", 4500.0, 4)
                    .build();

    public static final Query INSERT_INTO_TWO_EMPLOYEES =
            Queries.insertQueryInstance()
                    .insertInto("employee", "id", "name", "salary", "department_id")
                    .values(1, "John", 1000.0, 1)
                    .values(2, "Jane", 2000.5, 2)
                    .build();

    public static final Query INSERT_SAME_SALARY =
            Queries.insertQueryInstance()
                    .insertInto("employee", "id", "name", "salary")
                    .values(1, "John", 1000.0)
                    .values(2, "Jane", 1000.0)
                    .build();

    public static final Query SELECT_NAMES =
            Queries.selectQueryInstance()
                    .select(col("name").as("n"))
                    .from(table("employee"))
                    .build();

    public static final Query SELECT_ALL =
            Queries.selectQueryInstance()
                    .selectFrom(table("employee"))
                    .build();

    public static final Query SELECT_ALL_RENAMED =
            Queries.selectQueryInstance()
                    .selectFrom(table("employee_renamed"))
                    .build();

    public static final Query DROP_TABLE_EMPLOYEE =
            Queries.dropTableQueryInstance()
                    .dropTable("employee")
                    .build();

    public static final Query CREATE_INDEX_EMPLOYEE_NAME =
            Queries.createIndexQueryInstance()
                    .createIndex("employee_name_index")
                    .on("employee", ord("name").asc())
                    .build();

    public static final Query DELETE_EMPLOYEE =
            Queries.deleteQueryInstance()
                    .deleteFrom(table("employee"))
                    .where(
                            eq(col("name"), "John").and(gt(col("salary"), 900.0))
                    )
                    .build();

    public static final Query SELECT_EMPLOYEE =
            Queries.selectQueryInstance()
                    .select(col("name").as("employee_name"))
                    .from(table("employee"))
                    .build();

    public static final Query SELECT_EMPLOYEE_WITH_WHERE =
            Queries.selectQueryInstance()
                    .select(col("name").as("employee_name"))
                    .from(table("employee"))
                    .where(gt(col("salary"), 1000.0))
                    .build();

    public static final Query SELECT_EMPLOYEE_WITH_WHERE_AND_ORDER =
            Queries.selectQueryInstance()
                    .select(col("name"), col("salary"))
                    .from(table("employee"))
                    .where(gt(col("salary"), 1000.0))
                    .orderBy(ord("salary").desc())
                    .build();

    public static final Query SELECT_EMPLOYEE_WITH_WHERE_AND_ORDER_AND_LIMIT =
            Queries.selectQueryInstance()
                    .select(col("name"))
                    .from(table("employee"))
                    .where(gt(col("salary"), 1000.0))
                    .orderBy(ord("salary").desc())
                    .limit(1)
                    .build();

    public static final Query SELECT_EMPLOYEE_WITH_WHERE_AND_ORDER_AND_LIMIT_OFFSET =
            Queries.selectQueryInstance()
                    .select(col("name"))
                    .from(table("employee"))
                    .where(gt(col("salary"), 1000.0))
                    .orderBy(ord("salary").desc())
                    .limit(2)
                    .offset(1)
                    .build();

    public static final Query SELECT_EMPLOYEE_WITH_WHERE_AND_ORDER_AND_GROUP_BY_HAVING =
            Queries.selectQueryInstance()
                    .select(col("department_id").as("department"))
                    .from(table("employee"))
                    .where(gt(col("salary"), 1000.0))
                    .orderBy(ord("salary").desc())
                    .groupBy(col("department_id"))
                    .having(gt(avg("salary"), 4000.0))
                    .build();

    public static final Query SELECT_EMPLOYEE_AGGREGATE =
            Queries.selectQueryInstance()
                    .select(col("department_id"), sum("salary").as("total_salary"), count("name").as("count"), avg("salary").as("avg"), min("salary").as("min"), max("salary").as("max"))
                    .from(table("employee"))
                    .groupBy(col("department_id"))
                    .build();

    public static final Query SELECT_EMPLOYEE_AGGREGATE_HAVING =
            Queries.selectQueryInstance()
                    .select(sum("salary").as("total_salary"), count().as("count"), avg("salary").as("avg"), min("salary").as("min"), max("salary").as("max"))
                    .from(table("employee"))
                    .groupBy(col("department_id"))
                    .having((gt(col("salary"), 1000.0)).and(gt(col("count"), 2)))
                    .build();

    public static final Query UPDATE_EMPLOYEE =
            Queries.updateQueryInstance()
                    .update(table("employee"))
                    .set(col("salary"), 2000.0)
                    .where(eq(col("name"), "John"))
                    .build();

    public static final Query UPDATE_SET_AGE =
            Queries.updateQueryInstance()
                    .update(table("employee"))
                    .set(col("age"), 30)
                    .where(eq(col("name"), "John"))
                    .build();

    public static final Query ALTER_TABLE_EMPLOYEE_ADD_COLUMN =
            Queries.alterTableQueryInstance()
                    .alterTable("employee")
                    .addColumn("age", "INTEGER")
                    .build();

    public static final Query ALTER_TABLE_EMPLOYEE_RENAME =
            Queries.alterTableQueryInstance()
                    .alterTable("employee")
                    .renameTo("employee_renamed")
                    .build();

    public static final Query ALTER_TABLE_EMPLOYEE_DROP_COLUMN =
            Queries.alterTableQueryInstance()
                    .alterTable("employee")
                    .dropColumn("name")
                    .build();

    public static final Query SELECT_TABLE_WHERE_JOHN =
            Queries.selectQueryInstance()
                    .select(col("name").as("n"))
                    .from(table("employee"))
                    .where(eq(col("name"), "John"))
                    .build();

    public static final Query SELECT_TABLE_WHERE_JANE =
            Queries.selectQueryInstance()
                    .select(col("name"))
                    .from(table("employee"))
                    .where(eq(col("name"), "Jane"))
                    .build();


    private TestQueries() {
    }

}
