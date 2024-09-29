package graph_new;

import java.io.FileNotFoundException;
import java.util.Iterator;

/**
 * 算法实现类：找到和起点s 连通的所有顶点
 *
 * @author zhanpeng.jiang 2024/9/29
 */
public class Search {
    private Graph graph;
    private int s;
    private int connectedCount;

    /**
     * 搜索算法类
     *
     * @param graph 无向图
     * @param s     起点s
     */
    public Search(Graph graph, int s) {
        this.graph = graph;
        this.s = s;
        this.connectedCount = dfsCount(s, new boolean[graph.V()]);
    }

    /**
     * s是否与v连通
     *
     * @param v v
     * @return true if connected
     */
    public boolean marked(int v) {
        return dfsSearch(s, v, new boolean[graph.V()]);
    }

    /**
     * 与s连通的顶点数
     *
     * @return count
     */
    public int count() {
        return this.connectedCount;
    }

    private int dfsCount(int v, boolean[] marked) {
        marked[v] = true;
        // 这是通过成员变量的方式 统计
//        connectedCount++;

        // 通过返回值的方式统计, 访问当前节点，初始化count = 1
        int count = 1;

        Iterator<Integer> adj = graph.adj(v);
        while (adj.hasNext()) {
            Integer next = adj.next();
            if (!marked[next]) {
                // 加上 每个邻接节点 的连通顶点数统计
                count += dfsCount(next, marked);
            }
        }

        // 返回以 当前节点为起点 统计到的所有连通顶点数
        return count;
    }

    private boolean dfsSearch(int s, int v, boolean[] marked) {
        marked[s] = true;
        if (s == v) {
            return true;
        }
        Iterator<Integer> adj = graph.adj(s);

        while (adj.hasNext()) {
            int next = adj.next();
            if (!marked[next] && dfsSearch(next, v, marked)) {
                // 没访问过 且找到了，success fast
                return true;
            }
        }

        // 没找到，还需要继续找
        return false;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Graph graph = new Graph("/Users/zhanpeng/Documents/project/self/leetcode/src/graph_new/tinyG.txt");
        Search search = new Search(graph, 9);
        System.out.println(graph);
        System.out.println("connectedCount:" + search.count());
        System.out.println(search.marked(10));
        System.out.println(search.marked(4));
        System.out.println(search.marked(14));

        // 以s为起点，打印连通的顶点集
        System.out.println("Connected: ");
        for (int i = 0; i < graph.V(); i++) {
            if (search.marked(i)) {
                System.out.print(i + " ");
            }
        }
        System.out.println();

        if (search.count() != graph.V()) {
            System.out.println("Not connected");
        }
    }
}
