package test;

import java.util.LinkedList;

/**
 * 无向图
 *
 * @author zhanpeng.jiang 2024/4/13
 */
public class MyGraph {
    int nodeCount;
    LinkedList<Integer>[] adjs;

    public MyGraph(int v) {
        this.nodeCount = v;
        this.adjs = new LinkedList[nodeCount];
        for (int i = 0; i < nodeCount; i++) {
            adjs[i] = new LinkedList<>();
        }
    }

    public void addEdge(int node1, int node2) {
        adjs[node1].add(node2);
        adjs[node2].add(node1);
    }

    public boolean isCyclic() {
        for (int i = 0; i < nodeCount; i++) {
            boolean[] visited = new boolean[nodeCount];
            if (isCyclicSupport(visited, i, -1)) {
                return true;
            }
        }
        return false;
    }

    // i: 当前遍历的是节点
    private boolean isCyclicSupport(boolean[] visited, int node, int parent) {
        visited[node] = true;

        for (int adjNode : adjs[node]) {
            if (!visited[adjNode]) {
                isCyclicSupport(visited, adjNode, node);
            } else if (adjNode != parent) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        MyGraph myGraph = new MyGraph(3);
        myGraph.addEdge(0, 1);
        myGraph.addEdge(1, 2);
        myGraph.addEdge(0, 2);

        boolean cyclic = myGraph.isCyclic();
        if (cyclic) {
            System.out.println("有环");
        } else {
            System.out.println("无环");
        }
    }
}
