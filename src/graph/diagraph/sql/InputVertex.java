package graph.diagraph.sql;

import graph.diagraph.DiGraph;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 输入顶点
 *
 * @author zhanpeng.jiang 2024/4/22
 */
public class InputVertex implements DataModelVertex {

    private final int index;

    private final List<String> selectFields;

    private final String tableName;

    public InputVertex(int index, List<String> selectFields, String tableName) {
        this.index = index;
        this.selectFields = selectFields;
        this.tableName = tableName;
    }

    @Override
    public int indexOfGraph() {
        return index;
    }

    @Override
    public String generateSql(DiGraph graph) {
        StringBuilder sql = new StringBuilder();

        String alias = TableAliasGenerator.generateAlias();
        List<String> aliasFields =
                selectFields.stream().map(field -> String.join(".", alias, field)).collect(Collectors.toList());
        sql.append("SELECT ")
                .append(String.join(", ", aliasFields))
                .append(" FROM ")
                .append(tableName)
                .append(" ").append(alias);

        return sql.toString();
    }
}
