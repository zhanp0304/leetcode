package graph_new;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * CC: Connected Component 连通分量
 * <p>使用深度优先搜索 找出图中的所有连通分量</p>
 *
 * @author zhanpeng.jiang 2024/9/30
 */
public class CC {
    private boolean[] marked;
    private int count = 0;
    private int[] ids;

    public CC(Graph graph) {
        this.marked = new boolean[graph.V()];
        this.ids = new int[graph.V()];

        for (int i = 0; i < graph.V(); i++) {
            if (!marked[i]) {
                dfs(graph, i, count);
                count++;
            }
        }
    }

    private void dfs(Graph graph, int s, int id) {
        marked[s] = true;
        // 标识当前顶点 归属于 第几个连通分量
        ids[s] = id;

        Iterator<Integer> adj = graph.adj(s);

        while (adj.hasNext()) {
            Integer next = adj.next();
            if (!marked[next]) {
                dfs(graph, next, id);
            }
        }
    }


    /**
     * v与w是否连通
     *
     * @param v v
     * @param w w
     * @return
     */
    public boolean connected(int v, int w) {
        // id相同，说明归属于同一个连通分量下，那二者就是 连通的
        return ids[v] == ids[w];
    }

    /**
     * 连通分量的数量
     *
     * @return
     */
    public int count() {
        return count;
    }

    /**
     * 顶点v 属于哪个连通分量
     *
     * @param v
     * @return
     */
    public int id(int v) {
        return ids[v];
    }

    public static void main(String[] args) throws FileNotFoundException {
        Graph graph = new Graph("/Users/zhanpeng/Documents/project/self/leetcode/src/graph_new/tinyG.txt");
        CC cc = new CC(graph);
        System.out.println("Connected Components: " + cc.count());

        System.out.println("Components: \n");

        List<Integer>[] components = new ArrayList[cc.count()];
        for (int i = 0; i < cc.count(); i++) {
            components[i] = new ArrayList<>();
        }

        for (int i = 0; i < graph.V(); i++) {
            components[cc.id(i)].add(i);
        }

        for (int i = 0; i < cc.count; i++) {
            System.out.println(components[i]);
        }

        System.out.println(cc.connected(0, 3));
        System.out.println(cc.connected(0, 8));
        System.out.println(cc.connected(8, 9));
        System.out.println(cc.connected(9, 10));
    }
}
