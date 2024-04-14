package graph;

import java.util.LinkedList;

/**
 * 无向图基本API
 *
 * @author zhanpeng.jiang 2024/4/14
 */
public class MySimpleGraph {
    private final int V;
    private int E;
    private final LinkedList<Integer>[] adjs;

    public MySimpleGraph(int V) {
        this.V = V;
        this.adjs = new LinkedList[V];
        for (int i = 0; i < V; i++) {
            this.adjs[i] = new LinkedList<>();
        }
    }

    public void addEdge(int v, int w) {
        adjs[v].add(w);
        adjs[w].add(v);
        E++;
    }

    public Iterable<Integer> adjs(int v) {
        return adjs[v];
    }

    public int V() {
        return V;
    }

    public void print() {
        System.out.println(String.format("The graph has %s vertexes, %s edges", V, E));
        for (int i = 0; i < V; i++) {
            System.out.println(String.format("%s: ", i));
            for (int adj: adjs[i]) {
                System.out.println(adj + " ");
            }
            System.out.println("\n");
        }
    }
}
