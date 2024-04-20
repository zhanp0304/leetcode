package graph.sql;

import java.util.List;

public class Concatenator {
    private Concatenator() {

    }

    public static String concat(List<String> fieldNames) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < fieldNames.size(); i++) {
            result.append(fieldNames.get(i));
            if (i < fieldNames.size() - 1) {
                result.append(",");
            }
        }
        return result.toString();
    }
}
