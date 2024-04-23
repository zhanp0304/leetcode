package graph.diagraph.sql;

import graph.diagraph.DiGraph;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分组顶点
 * TODO：后续在提供接口获取分组顶点的fields给前端时，需要校验前置父节点，只允许有一个
 *
 * @author zhanpeng.jiang 2024/4/22
 */
public class GroupByVertex implements DataModelVertex {

    private final int index;

    private final List<String> selectAggregateFields;

    private final List<String> groupByFields;

    private final String tableName;

    private final DataModelVertex[] vertices;

    public GroupByVertex(int index, List<String> selectAggregateFields,
                         List<String> groupByFields, String tableName, DataModelVertex[] vertices) {
        this.index = index;
        this.selectAggregateFields = selectAggregateFields;
        this.groupByFields = groupByFields;
        this.tableName = tableName;
        this.vertices = vertices;
    }

    @Override
    public int indexOfGraph() {
        return index;
    }

    @Override
    public ModelSqlResult generateSql(DiGraph graph) {
        List<Integer> neighbors = graph.adjacent(index);

        if (neighbors.size() != 1) {
            throw new IllegalArgumentException("Only one neighbor can be connected by vertex of Group-By type");
        }
        ModelSqlResult modelSqlResult = vertices[neighbors.get(0)].generateSql(graph);

        StringBuilder sql = new StringBuilder();

        String alias = TableAliasGenerator.generateAlias();
        List<String> aliasFields = SqlFieldsHelper.getAliasFields(selectAggregateFields, alias);
        List<String> aliasGroupByFields = SqlFieldsHelper.getAliasFields(groupByFields, alias);

        sql.append("SELECT ")
                .append(String.join(", ", aliasFields))
                .append(" FROM ").append("( ").append(modelSqlResult.getSql()).append(" )")
                .append(tableName)
                .append(" ").append(alias)
                .append("GROUP BY ").append(aliasGroupByFields);

        return new ModelSqlResult(sql.toString(), selectAggregateFields);
    }
}
