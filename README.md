# SQLike

An in-memory SQL-like relational database engine implemented in Java.
Developed as a pair project for the course **PB162 Java Programming** at the Faculty of Informatics, Masaryk University (spring 2025).

## About

SQLike is a database engine inspired by SQLite. It supports a fluent query builder API for constructing and executing SQL-like commands for an in-memory database with possibility of CSV export.

## Features

- `CREATE TABLE` / `DROP TABLE` / `ALTER TABLE`
- `INSERT INTO`, `SELECT`, `UPDATE`, `DELETE`
- `SELECT` with `WHERE`, `GROUP BY`, `HAVING`, `ORDER BY`, `LIMIT`, `OFFSET`
- CSV export of `SELECT` query results
- Supported data types: `INTEGER`, `REAL`, `TEXT`

## Tech Stack

- Java 21
- Maven

## Usage

```java
// Create a table
database.execute(
    Queries.createTableQuery()
        .createTable("employee")
        .column("id", "INTEGER")
        .column("name", "TEXT")
        .column("age", "INTEGER")
        .primaryKey("id")
        .build()
);

// Insert rows
database.execute(
    Queries.insertQuery()
        .insertInto("employee", "id", "name", "age")
        .values(1, "Jane", 28)
        .values(2, "John", 35)
        .build()
);

// Query with conditions
database.execute(
    Queries.selectQuery()
        .select("name", "age")
        .from("employee")
        .where(Dsl.column("age").gt(25))
        .orderBy(Dsl.column("name").asc())
        .build()
);
```

## Run Tests

```bash
mvn test
```

## My contributions

- `data.database`, `data.type`
- `execute` package
- `query.result` — QueryResult, ResultRow
- `query` — INSERT, SELECT (with OFFSET, LIMIT, GROUP BY, HAVING, ORDER BY), ALTER TABLE
- `query.builder` — SelectQueryBuilder extensions, AlterTableQueryBuilder

## Note

This project was originally developed on the Faculty of Informatics GitLab as part of a pair assignment. The original commit history is not preserved. The design was interface-driven, with implementation left to students.
