package graph.diagraph.sql;

import com.sun.istack.internal.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        return assembleFields(selectFields, alias, false);
    }

    public static List<String> getAliasFields(List<String> selectFields, String alias) {
        return assembleFields(selectFields, alias, false);
    }

    public static List<String> getAliasFields(List<String> selectFields, String alias, boolean withAlias) {
        return assembleFields(selectFields, alias, withAlias);
    }

    private static List<String> assembleFields(List<String> selectFields, String alias, boolean withAlias) {
        return assembleFields(selectFields, alias, Collections.emptyMap(), withAlias);
    }

    public static List<String> assembleFields(List<String> selectFields, String aliasTable, @NotNull Map<String, String> aliasFieldMap,
                                              boolean withAlias) {
        return selectFields.stream()
                .map(field -> {
                    String aliasFieldName = Optional.ofNullable(aliasFieldMap.get(field)).orElse(field);
                    aliasFieldName = withAlias ? " AS " + aliasFieldName : "";
                    return String.join(".", aliasTable, field) + aliasFieldName;
                })
                .collect(Collectors.toList());
    }
}
