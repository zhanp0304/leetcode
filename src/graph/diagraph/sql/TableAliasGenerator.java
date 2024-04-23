package graph.diagraph.sql;

import java.util.UUID;

/**
 * TableAliasGenerator
 *
 * @author zhanpeng.jiang 2024/4/22
 */
public class TableAliasGenerator {
    private TableAliasGenerator() {

    }

    public static String generateAlias() {
        String prefix = "Alias_" + UUID.randomUUID().toString().replace("-", "").substring(0, 5);
        return prefix + System.nanoTime();
    }
}
