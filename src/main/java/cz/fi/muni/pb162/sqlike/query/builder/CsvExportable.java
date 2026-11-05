package cz.fi.muni.pb162.sqlike.query.builder;

/**
 * Interface for query builders that support CSV export. Result of this query is exported to CSV file.
 * <p>
 * The default delimiter is a comma (',')
 * The default charset is UTF-8.
 * The default behavior is to assume that the CSV file has a header.
 *
 * @param <Q> the type of the query builder
 */
public interface CsvExportable<Q extends QueryBuilder> {


    /**
     * Exports the result of the query to a CSV file.
     *
     * @param fileName the name of the CSV file
     * @return the query builder
     */
    Q exportToCsv(String fileName);

    /**
     * Exports the result of the query to a CSV file with a specified delimiter.
     *
     * @param fileName  the name of the CSV file
     * @param delimiter the delimiter used in the CSV file
     * @return the query builder
     */
    Q exportToCsv(String fileName, String delimiter);

    /**
     * Exports the result of the query to a CSV file with a specified delimiter and header.
     *
     * @param fileName   the name of the CSV file
     * @param delimiter  the delimiter used in the CSV file
     * @param withHeader true if the CSV file should have a header, false otherwise
     * @return the query builder
     */
    Q exportToCsv(String fileName, String delimiter, boolean withHeader);

    /**
     * Exports the result of the query to a CSV file with a specified delimiter, header, and charset.
     *
     * @param fileName    the name of the CSV file
     * @param delimiter   the delimiter used in the CSV file
     * @param withHeader  true if the CSV file should have a header, false otherwise
     * @param charsetName the name of the charset to use
     * @return the query builder
     */
    Q exportToCsv(String fileName, String delimiter, boolean withHeader, String charsetName);
}
