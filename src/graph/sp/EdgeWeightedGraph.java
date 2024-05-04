package graph.sp;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 加权有向图
 *
 * @author zhanpeng.jiang 2024/5/4
 */
public class EdgeWeightedGraph {
    private final int vertexCount;
    private int edgeCount;
    private final LinkedList<DirectedEdge>[] adjacent;

    public EdgeWeightedGraph(int vertexCount) {
        this.vertexCount = vertexCount;
        this.adjacent = new LinkedList[vertexCount];
        for (int i = 0; i < vertexCount; i++) {
            adjacent[i] = new LinkedList<>();
        }
    }

    public int verticals() {
        return vertexCount;
    }

    public int edgeCount() {
        return edgeCount;
    }

    public void addEdge(DirectedEdge edge) {
        adjacent[edge.from()].add(edge);
        edgeCount++;
    }

    public Iterable<DirectedEdge> edges() {
        List<DirectedEdge> allEdges = new ArrayList<>();
        for (LinkedList<DirectedEdge> edgesOfAdjacent : adjacent) {
            allEdges.addAll(edgesOfAdjacent);
        }
        return allEdges;
    }

    public Iterable<DirectedEdge> adj(int vertex) {
        return adjacent[vertex];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Vertex count: ").append(vertexCount).append("\n");
        sb.append("Edge count: ").append(edgeCount).append("\n");
        for (int v = 0; v < vertexCount; v++) {
            for (DirectedEdge edge : adjacent[v]) {
                sb.append(edge.toString()).append("\n");
            }
        }
        return sb.toString();
    }
}
