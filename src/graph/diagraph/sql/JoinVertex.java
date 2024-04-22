package graph.diagraph.sql;

import graph.diagraph.DiGraph;

import java.util.List;

/**
 * 连接顶点
 * TODO：后续在提供接口获取分组顶点的fields给前端时，需要校验前置父节点，只允许有两个
 *
 * @author zhanpeng.jiang 2024/4/22
 */
public class JoinVertex implements DataModelVertex {

    private final int index;

    private final DataModelVertex[] vertices;

    private final JoinTypeEnum joinType;

    public JoinVertex(int index, DataModelVertex[] vertices, JoinTypeEnum joinType) {
        this.index = index;
        this.vertices = vertices;
        this.joinType = joinType;
    }

    @Override
    public int indexOfGraph() {
        return index;
    }

    @Override
    public String generateSql(DiGraph graph) {
        List<Integer> neighbors = graph.adjacent(index);

        if (neighbors.size() != 1) {
            throw new IllegalArgumentException("Only one neighbor can be connected by vertex of Group-By type");
        }

        String leftNeighborSql = vertices[neighbors.get(0)].generateSql(graph);
        String rightNeighborSql = vertices[neighbors.get(1)].generateSql(graph);

        StringBuilder sql = new StringBuilder();

        // TODO： 传递与聚合上一层select fields作为当前的select字段与on 连接字段名

        if (JoinTypeEnum.LEFT_JOIN == joinType) {
            sql.append("SELECT t1.*")
                    .append(" FROM ").append("( ").append(leftNeighborSql).append(" ) ").append("t1")
                    .append("LEFT JOIN ").append("( ").append(rightNeighborSql).append(" ) ").append("t2")
                    // TODO： fix xxx
                    .append("ON t1.").append("xxx").append(" = t2.").append("yyy");
        }

        return sql.toString();
    }
}
