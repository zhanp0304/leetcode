package graph.diagraph.sql;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper for sql fields
 *
 * @author zhanpeng.jiang 2024/4/23
 */
public class SqlFieldsHelper {
    private SqlFieldsHelper() {

    }

    public static List<String> getAliasFields(List<String> selectFields) {
        String alias = TableAliasGenerator.generateAlias();
        return assembleFields(selectFields, alias);
    }

    public static List<String> getAliasFields(List<String> selectFields, String alias) {
        return assembleFields(selectFields, alias);
    }

    private static List<String> assembleFields(List<String> selectFields, String alias) {
        return selectFields.stream().map(field -> String.join(".", alias, field) + " AS " + field).collect(Collectors.toList());
    }
}
