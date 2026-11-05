package cz.fi.muni.pb162.sqlike.query.result;

import cz.fi.muni.pb162.sqlike.data.table.row.Row;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static cz.fi.muni.pb162.sqlike.query.result.QueryResultStatus.ERROR;

/**
 * This class represents QueryResult.
 *
 * @author Valeria Kvassayova
 */
public class QueryResultImpl implements QueryResult {
    private final Status status;
    private final Optional<Iterable<ResultRow>> payload;
    private final int rowsAffected;

    public QueryResultImpl(Status status, Optional<Iterable<ResultRow>> payload, int rowsAffected) {
        this.status = status;
        this.payload = payload;
        this.rowsAffected = rowsAffected;
    }

    @Override
    public Status status() {
        return status;
    }

    @Override
    public Optional<Iterable<ResultRow>> payload() {
        return payload;
    }

    @Override
    public int rowsAffected() {
        return rowsAffected;
    }

    @Override
    public Optional<String> errorMessage() {
        return status == ERROR ? Optional.of("ERROR: Query failed.") : Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof QueryResultImpl result) {
            if (result.payload.isEmpty() && payload.isEmpty()) {
                return result.status.equals(status);
            }
            List<Iterable<ResultRow>> toCompare;
            if (payload.isPresent()) {
                toCompare = payload.stream().toList();
            } else {
                return false;
            }
            boolean equal = result.payload.stream().allMatch(toCompare::contains);
            return result.status.equals(status) && equal && result.payload.isPresent();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return status.hashCode() + Arrays.hashCode(payload.stream().toArray());
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (payload.isEmpty()) {
            return builder.toString();
        }
        boolean first = true;
        for (ResultRow row : payload.orElse(List.of())) {
            if (!first) {
                builder.append(System.lineSeparator());
            }
            first = false;
            builder.append(row.toString());
        }
        return builder.toString();
    }
}
