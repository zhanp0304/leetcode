package graph;

import java.util.LinkedList;

/**
 * Description
 *
 * @author zhanpeng.jiang 2024/4/13
 */
class Graph {
    private int V; // 顶点数
    private LinkedList<Integer>[] adj; // 邻接表表示的图

    Graph(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
    }

    // 添加边
    void addEdge(int v, int w) {
        adj[v].add(w); // 无向图，所以需要添加两次
        adj[w].add(v);
    }

    // 深度优先搜索
    private boolean isCyclicUtil(int v, boolean[] visited, int parent) {
        visited[v] = true;

        // 递归访问每个邻接点
        for (Integer i : adj[v]) {
            if (!visited[i]) {
                if (isCyclicUtil(i, visited, v))
                    return true;
            } else if (i != parent) // 如果邻接点已经被访问过，且不是当前节点的父节点，则说明存在环路
                return true;
        }
        return false;
    }

    // 检测图中是否存在环路（短路）
    boolean isCyclic() {
        boolean[] visited = new boolean[V]; // 记录节点是否被访问过

        // 遍历每个节点，如果发现某个节点未被访问过，则从该节点开始深度优先搜索
        for (int i = 0; i < V; ++i)
            if (!visited[i])
                if (isCyclicUtil(i, visited, -1)) // 使用 -1 表示起始节点的父节点为不存在的节点
                    return true;

        return false;
    }
}
