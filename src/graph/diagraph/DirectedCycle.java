package graph.diagraph;

import java.util.Stack;

/**
 * 有向无环图检测类
 *
 * @author zhanpeng.jiang 2024/4/20
 */
public class DirectedCycle {
    private int[] edgeTo;
    private boolean visited[];
    private boolean onStack[];
    /**
     * Record the cycle path with stack
     */
    private Stack<Integer> cycle;

    public DirectedCycle(DiaGraph diaGraph) {
        onStack = new boolean[diaGraph.verticals()];
        edgeTo = new int[diaGraph.verticals()];
        visited = new boolean[diaGraph.verticals()];

        for (int vertex = 0; vertex < diaGraph.verticals(); vertex++) {
            if (!visited[vertex]) {
                dfs(diaGraph, vertex);
            }
        }
    }

    public void dfs(DiaGraph diaGraph, int vertex) {
        visited[vertex] = true;
        onStack[vertex] = true;

        for (int neighbor : diaGraph.adjacent(vertex)) {
            if (hasCycle()) {
                return;
            }
            if (!visited[neighbor]) {
                edgeTo[neighbor] = vertex;
                dfs(diaGraph, neighbor);
            } else if (onStack[neighbor]) {
                cycle = new Stack<>();
                // Detect a cycle
                for (int currentVertex = vertex; currentVertex != neighbor; currentVertex = edgeTo[currentVertex]) {
                    cycle.push(currentVertex);
                }
                cycle.push(neighbor);
                cycle.push(vertex);
            }
        }
        // Backtracking
        onStack[vertex] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }
}
