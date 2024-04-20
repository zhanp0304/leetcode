package graph.diagraph;

import java.util.LinkedList;
import java.util.List;

/**
 * 有向图
 *
 * @author zhanpeng.jiang 2024/4/20
 */
public class DiGraph {
    private int nodes;
    private LinkedList<Integer>[] adjacent;

    public DiGraph(int nodes) {
        this.nodes = nodes;
        this.adjacent = new LinkedList[nodes];
        for (int i = 0; i < nodes; i++) {
            this.adjacent[i] = new LinkedList<>();
        }
    }

    public void addEdge(int v, int w) {
        adjacent[v].add(w);
    }

    public int verticals() {
        return nodes;
    }

    public List<Integer> adjacent(int v) {
        return adjacent[v];
    }
}
