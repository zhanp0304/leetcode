package graph_new.shortest_path;

import graph.sp.DirectedEdge;
import graph.sp.EdgeWeightedGraph;
import graph.sp.GraphReader;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.PriorityQueue;

/**
 * DijkstraSP
 *
 * @author zhanpeng.jiang 2024/10/20
 */
public class NetworkDelayTime {
    /**
     * edgeTo[w] v -> w，在s -> w的最短路径上，the last edge to w [from = v, to = w]
     */
    private DirectedEdge[] edgeTo;
    /**
     * distTo[w]： s -> w的最短距离
     */
    private Double[] distTo;
    /**
     * 优先队列在这里的作用是：每次迭代遍历中，都能够从优先队列中 取当前不在SPT（最短路径树）上且离源节点s最近的未处理顶点
     */
    private PriorityQueue<WeightForDistToW> minPq;

    private int unProcessedCount;

    private double maxDelayTime;

    private boolean[] marked;

    public NetworkDelayTime(EdgeWeightedGraph graph, int s) {
        // Init
        this.edgeTo = new DirectedEdge[graph.verticals()];
        this.distTo = new Double[graph.verticals()];
        // 用优先队列可以减少relax条件命中的次数，从而减少relax真正操作的次数，直接将复杂度从O(V ^ E) 优化到 O((V+E) * logV)
        this.minPq = new PriorityQueue<>();

        for (int v = 0; v < graph.verticals(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;

        // 记录未处理顶点数（用于判断是否存在不可达顶点）
        this.marked = new boolean[graph.verticals()];
        this.unProcessedCount = graph.verticals();
        // 最长延时时间
        this.maxDelayTime = Double.NEGATIVE_INFINITY;

        // Build the SPT
        Iterable<DirectedEdge> adj = graph.adj(s);
        for (DirectedEdge edge : adj) {
            int w = edge.to();
            minPq.add(new WeightForDistToW(edge.weight(), w));
            distTo[w] = edge.weight();
            edgeTo[w] = edge;
            maxDelayTime = Math.max(maxDelayTime, edge.weight());
        }
        marked[s] = true;

        while (!minPq.isEmpty()) {
            WeightForDistToW currentMin = minPq.poll();
            int needProcessedVertex = currentMin.vertex;
            relax(graph, needProcessedVertex);
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v].compareTo(Double.POSITIVE_INFINITY) != 0;
    }

    public Iterator<DirectedEdge> pathTo(int v) {
        Deque<DirectedEdge> pathStack = new ArrayDeque<>();
        for (DirectedEdge edge = edgeTo[v]; edge != null; edge = edgeTo[edge.from()]) {
            pathStack.push(edge);
        }
        return pathStack.iterator();
    }

    private void relax(EdgeWeightedGraph graph, int needProcessedV) {
        if (!marked[needProcessedV]) {
            marked[needProcessedV] = true;
            --unProcessedCount;
        }

        for (DirectedEdge adjEdge : graph.adj(needProcessedV)) {
            // distTo[w] > distTo[needProcessedV] + e.weight, then need relax
            int w = adjEdge.to();
            int v = adjEdge.from();
            if (distTo[w] > (distTo[v] + adjEdge.weight())) {
                distTo[w] = distTo[v] + adjEdge.weight();
                edgeTo[w] = adjEdge;

                WeightForDistToW weightForDistToW = new WeightForDistToW(adjEdge.weight(), w);
                if (minPq.contains(weightForDistToW)) {
                    // Update the existed element's weight within minPq
                    minPq.remove(weightForDistToW);
                    minPq.add(weightForDistToW);
                } else {
                    minPq.add(weightForDistToW);
                }
            }

        }
    }

    public static void main(String[] args) {
        EdgeWeightedGraph graph = GraphReader.readGraphFromFile("src/graph/sp/tinyEWD.txt");
        assert graph != null;
        NetworkDelayTime dijkstra = new NetworkDelayTime(graph, 0);
        Iterator<DirectedEdge> sp = dijkstra.pathTo(6);
        System.out.println("Shortest path: ");
        while (sp.hasNext()) {
            DirectedEdge edge = sp.next();
            System.out.println(String.format("%s->%s", edge.from(), edge.to()));
        }

        System.out.println("cost: " + dijkstra.distTo(6));
    }
}
