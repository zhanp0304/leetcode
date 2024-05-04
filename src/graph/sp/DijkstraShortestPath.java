package graph.sp;

import java.util.*;

/**
 * 最短路径 - Dijkstra
 *
 * @author zhanpeng.jiang 2024/5/4
 */
public class DijkstraShortestPath {

    /**
     * distTo[w] is the distance from 's' to 'w' within the shortest path
     */
    private double[] distTo;

    /**
     * edgeTo[w] is the final edge to vertex - 'w' within the shortest path
     */
    private DirectedEdge[] edgeTo;

    /**
     * priority queue for acquiring the vertex with minimum distance from s currently
     */
    private PriorityQueue<VertexIndexWeight> minPq;


    public DijkstraShortestPath(EdgeWeightedGraph edgeWeightedGraph, int s) {
        this.distTo = new double[edgeWeightedGraph.verticals()];
        this.edgeTo = new DirectedEdge[edgeWeightedGraph.verticals()];
        this.minPq = new PriorityQueue<>(edgeWeightedGraph.verticals());

        // Initialization
        Arrays.fill(distTo, Double.MAX_VALUE);
        distTo[s] = 0.0;

        // Find the shortest path
        minPq.add(new VertexIndexWeight(s, 0));
        while (!minPq.isEmpty()) {
            relax(edgeWeightedGraph, minPq.poll());
        }
    }

    private void relax(EdgeWeightedGraph graph, VertexIndexWeight tmp) {
        int whitePoint = tmp.getVertex();
        for (DirectedEdge toEdge : graph.adj(whitePoint)) {
            int bluePoint = toEdge.to();
            if (distTo[bluePoint] > distTo(whitePoint) + toEdge.weight()) {
                distTo[bluePoint] = distTo(whitePoint) + toEdge.weight();
                edgeTo[bluePoint] = toEdge;

                VertexIndexWeight indexWeight = new VertexIndexWeight(bluePoint, -1);
                if (minPq.contains(indexWeight)) {
                    minPq.remove(indexWeight);
                    minPq.add(new VertexIndexWeight(toEdge));
                } else {
                    minPq.add(new VertexIndexWeight(toEdge));
                }
            }
        }
    }

    /**
     * 从起点s到目的地v的最短距离
     *
     * @param vertex vertex
     * @return shortest distance
     */
    public double distTo(int vertex) {
        return distTo[vertex];
    }

    /**
     * 从起点s到目的地v是否有可行路径
     *
     * @param vertex vertex
     * @return true if path exists
     */
    public boolean hasPathTo(int vertex) {
        return edgeTo[vertex] != null;
    }

    /**
     * 从起点s到vertex的最短路径
     *
     * @param vertex vertex
     * @return shortest path track
     */
    public Iterator<DirectedEdge> pathTo(int vertex) {
        Deque<DirectedEdge> pathStack = new ArrayDeque<>();
        for (DirectedEdge edge = edgeTo[vertex]; edge != null; edge = edgeTo[edge.from()]) {
            pathStack.offerLast(edge);
        }
        return pathStack.descendingIterator();
    }


}
