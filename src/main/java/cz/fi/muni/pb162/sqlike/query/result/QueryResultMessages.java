package cz.fi.muni.pb162.sqlike.query.result;

public interface QueryResultMessages {


    static String tableNotFound(String tableName) {
        return String.format("Table %s not found.", tableName);
    }

    static String tableAlreadyExists(String tableName) {
        return String.format("Table %s already exists.", tableName);
    }

    static String unsupportedQueryType(String queryType) {
        return String.format("Unsupported query type: %s", queryType);
    }


    static String insertFailed() {
        return "Insert failed.";
    }
}
