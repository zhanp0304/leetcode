package graph.sp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class GraphReader {

    public static EdgeWeightedGraph readGraphFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            int vertexCount = Integer.parseInt(br.readLine().trim());
            int edgeCount = Integer.parseInt(br.readLine().trim());
            EdgeWeightedGraph graph = new EdgeWeightedGraph(vertexCount);
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                int from = Integer.parseInt(parts[0]);
                int to = Integer.parseInt(parts[1]);
                double weight = Double.parseDouble(parts[2]);
                DirectedEdge edge = new DirectedEdge(from, to, weight);
                graph.addEdge(edge);
            }
            return graph;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        EdgeWeightedGraph graph = readGraphFromFile("src/graph/sp/tinyEWD.txt");
        System.out.println("Graph: " + graph);
    }
}
