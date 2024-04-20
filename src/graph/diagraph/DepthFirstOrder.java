package graph.diagraph;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * 深度优先遍历 - DAG
 * <p>Note: pre前序遍历：dfs()前推入队列</p>
 * <p>Note: post后序遍历：dfs()后推入队列</p>
 * <p>Note: 逆后序遍历（aka 拓扑排序）：dfs()后压入栈</p>
 *
 * @author zhanpeng.jiang 2024/4/20
 */
public class DepthFirstOrder {
    private final boolean[] marked;
    private final Deque<Integer> preOrders;
    private final Deque<Integer> postOrders;

    public DepthFirstOrder(DiaGraph diaGraph) {
        this.preOrders = new LinkedList<>();
        this.postOrders = new LinkedList<>();
        this.marked = new boolean[diaGraph.verticals()];

        DirectedCycle directedCycle = new DirectedCycle(diaGraph);
        if (directedCycle.hasCycle()) {
            throw new IllegalArgumentException("The diagraph should not have cycle");
        }
        for (int i = 0; i < diaGraph.verticals(); i++) {
            if (!marked[i]) {
                dfs(diaGraph, i);
            }
        }
    }

    public void dfs(DiaGraph diaGraph, int vertex) {
        marked[vertex] = true;
        preOrders.offer(vertex);

        for (int neighbor : diaGraph.adjacent(vertex)) {
            if (!marked[neighbor]) {
                dfs(diaGraph, neighbor);
            }
        }
        postOrders.offer(vertex);
    }

    public Iterator<Integer> preOrder() {
        return preOrders.iterator();
    }

    public Iterator<Integer> postOrder() {
        return postOrders.iterator();
    }

    /**
     * Deque preform like a stack to save redundant space to record reversed-post-order
     */
    public Iterator<Integer> reversedPostOrder() {
        // Perform as Stack
        return postOrders.descendingIterator();
    }
}
