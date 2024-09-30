package graph_new;

import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

/**
 * 无向图 Bfs
 *
 * @author zhanpeng.jiang 2024/9/30
 */
public class BreadFirstPaths {
    private boolean[] marked;
    // index: vertex, value: [xxx] -> vertex
    private int[] edgeTo;

    public BreadFirstPaths(Graph graph, int s) {
        this.marked = new boolean[graph.V()];
        this.edgeTo = new int[graph.V()];
        bfs(graph, s);
    }

    public void bfs(Graph graph, int s) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(s);
        marked[s] = true;

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            Iterator<Integer> adj = graph.adj(vertex);
            while (adj.hasNext()) {
                Integer next = adj.next();
                if (!marked[next]) {
                    marked[next] = true;
                    edgeTo[next] = vertex;
                    queue.offer(next);
                }
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public static void main(String[] args) throws FileNotFoundException {
        Graph graph = new Graph("/Users/zhanpeng/Documents/project/self/leetcode/src/graph_new/tinyG.txt");
        BreadFirstPaths breadFirstPaths = new BreadFirstPaths(graph, 0);
        boolean hasPath = breadFirstPaths.hasPathTo(9);
        System.out.println(hasPath);
    }
}
