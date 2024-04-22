package graph.diagraph.sql;

import graph.diagraph.DiGraph;

/**
 * 基于DAG的SQL生成器
 * <p>Note: 尝试逆向实现一次X-Pro做的低代码数据建模</p>
 *
 * @author zhanpeng.jiang 2024/4/22
 */
public class DagSqlGenerator {
    private final boolean[] visited;
    private final DiGraph graph;
    private final String[] sqlBuffer;
    private final DataModelVertex[] dataModelVertices;

    public DagSqlGenerator(DiGraph graph, DataModelVertex[] dataModelVertices) {
        this.sqlBuffer = new String[graph.verticals()];
        this.visited = new boolean[graph.verticals()];
        this.graph = graph;
        this.dataModelVertices = dataModelVertices;
    }


}
