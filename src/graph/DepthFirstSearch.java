package graph;

/**
 * 无向图的深度优先搜索
 * <p>这里是个辅助类，在构造函数中，会基于给定的某个节点s，然后在给定图中完成递归操作，并把过程都记录到marked数组中</p>
 * <p><主要有marked方法: 用于记录当前节点是否被遍历过, 这个被测试类用于判断图中当前节点是否被遍历过，若有，就说明节点s可到达当前节点</p>
 * <p>count()函数，用于和图中总顶点数V做比较，若相同，则说明整个图是连通的，否则，就说明该图不是连通的，存在部分节点无法从s抵达</p>
 *
 * @author zhanpeng.jiang 2024/4/14
 */
public class DepthFirstSearch {
    private final boolean[] marked;
    private int count;

    public DepthFirstSearch(MySimpleGraph graph, int startVertex) {
        this.marked = new boolean[graph.V()];
        this.count = 0;
        dfsTraverseFromVertex(graph, startVertex);
    }

    private void dfsTraverseFromVertex(MySimpleGraph graph, int startVertex) {
        marked[startVertex] = true;
        count++;
        for (int adj : graph.adjs(startVertex)) {
            if (!marked[adj]) {
                dfsTraverseFromVertex(graph, adj);
            }
        }
    }

    public boolean marked(int v) {
        return marked[v];
    }

    public int count() {
        return count;
    }
}
