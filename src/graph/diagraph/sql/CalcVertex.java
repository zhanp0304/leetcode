package graph.diagraph.sql;

import graph.diagraph.DiGraph;

import java.util.List;

/**
 * 计算列
 *
 * @author zhanpeng.jiang 2024/4/23
 */
public class CalcVertex implements DataModelVertex {

    private final int index;

    private final List<String> selectFields;

    private final DataModelVertex[] vertices;

    public CalcVertex(int index, List<String> selectFields, DataModelVertex[] vertices) {
        this.index = index;
        this.selectFields = selectFields;
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
            throw new IllegalArgumentException("CalcVertex must have exactly one neighbor");
        }
        DataModelVertex neighbor = vertices[neighbors.get(0)];
        ModelSqlResult sqlResult = neighbor.generateSql(graph);

        StringBuilder sql = new StringBuilder();

        String alias = TableAliasGenerator.generateAlias();
        List<String> aliasFields = SqlFieldsHelper.getAliasFields(selectFields, alias);
        sql.append("SELECT ")
                .append(String.join(", ", aliasFields))
                .append(" FROM ")
                .append(" ( ").append(sqlResult.getSql()).append(" )")
                .append(" ").append(alias);

        return new ModelSqlResult(sql.toString(), selectFields);
    }
}
