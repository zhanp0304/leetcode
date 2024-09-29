package graph_new;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * 无向图
 *
 * @author zhanpeng.jiang 2024/9/29
 */
public class Graph {
    // 顶点数目
    private int V;

    // 边的数目
    private int E;

    // 邻接表
    private LinkedList<Integer>[] adj;

    public Graph(int V) {
        this.V = V;
        adj = new LinkedList[V];

        for (int i = 0; i < V; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    public Graph(Scanner scanner) {
        // 读取顶点数目V，初始化图
        this(scanner.nextInt());
        // 读取边的数目
        this.E = scanner.nextInt();
        // 添加边
        for (int i = 0; i < V; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            addEdge(from, to);
        }
    }

    public int V() {
        return V;
    }


    public void addEdge(int v, int w) {
        // 因为是无向图，所以两个顶点互相邻接
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public Iterator<Integer> adj(int v) {
        return adj[v].iterator();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s vertices, %s edges\n", V, E));
        for (int v = 0; v < V; v++) {
            sb.append(v + ": ");
            // 保持与《算法》中的bag类型一致的顺序
            Iterator<Integer> iterator = adj[v].descendingIterator();
            while (iterator.hasNext()) {
                sb.append(iterator.next()).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        Graph graph = new Graph(new Scanner(System.in));
        System.out.println(graph);
    }
}
