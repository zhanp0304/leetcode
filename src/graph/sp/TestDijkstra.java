package graph.sp;

import java.util.Iterator;

/**
 * Description
 *
 * @author zhanpeng.jiang 2024/5/4
 */
public class TestDijkstra {
    public static void main(String[] args) {
        EdgeWeightedGraph graph = GraphReader.readGraphFromFile("src/graph/sp/tinyEWD.txt");
        DijkstraShortestPath dijkstra = new DijkstraShortestPath(graph, 0);
        Iterator<DirectedEdge> sp = dijkstra.pathTo(6);
        System.out.println("Shortest path: ");
        while (sp.hasNext()) {
            DirectedEdge edge = sp.next();
            System.out.println(String.format("%s->%s", edge.from(), edge.to()));
        }

        System.out.println("cost: " + dijkstra.distTo(6));
    }
}
