package graph.diagraph.sql;

import graph.diagraph.DiGraph;

/**
 * 数据模型顶点接口
 *
 * @author zhanpeng.jiang 2024/4/22
 */
public interface DataModelVertex {
    /**
     * 先继续沿用index整形数组记录顶点
     *
     * @return index of the graph
     */
    int indexOfGraph();

    /**
     * 根据neighbors生成当前顶点的sql
     * <p>TODO： 需要添加全局缓存，避免重复计算</p>
     *
     * @param graph graph
     * @return sql
     */
    ModelSqlResult generateSql(DiGraph graph);
}
