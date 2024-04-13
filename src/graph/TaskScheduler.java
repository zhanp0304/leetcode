package graph;

import java.util.*;

class DiaGraph {
    private final int V; // 顶点数
    private final LinkedList<Integer>[] adj; // 邻接表
    private final String[] nodeNames; // 节点名称数组

    DiaGraph(int v, String[] names) {
        V = v;
        adj = new LinkedList[v];
        nodeNames = names;
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList<>();
    }

    // 添加有向边
    void addEdge(int v, int w) {
        adj[v].add(w);
    }

    // 拓扑排序
    void topologicalSortUtil(int v, boolean[] visited, Stack<Integer> stack) {
        visited[v] = true;

        for (Integer i : adj[v]) {
            if (!visited[i])
                topologicalSortUtil(i, visited, stack);
        }

        stack.push(v);
    }

    void topologicalSort() {
        Stack<Integer> stack = new Stack<>();
        boolean[] visited = new boolean[V];

        for (int i = 0; i < V; i++) {
            if (!visited[i])
                topologicalSortUtil(i, visited, stack);
        }

        System.out.println("任务调度顺序：");
        while (!stack.empty()) {
            System.out.print(nodeNames[stack.pop()] + " ");
        }
    }
}

public class TaskScheduler {
    public static void main(String args[]) {
        String[] taskNames = {"start", "master-data", "product", "inventory", "coupon-center", "end"}; // 节点名称数组
        int tasks = taskNames.length; // 总任务数
        DiaGraph g = new DiaGraph(tasks, taskNames);

        // 添加任务之间的依赖关系，表示为有向边
        g.addEdge(0, 1); // start -> master-data
        g.addEdge(1, 2); // master-data -> product
        g.addEdge(1, 3); // master-data -> inventory
        g.addEdge(2, 4); // product -> coupon-center
        g.addEdge(3, 4); // inventory -> coupon-center
        g.addEdge(4, 5); // coupon-center -> end

        g.topologicalSort();
    }
}
