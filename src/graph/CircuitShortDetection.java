package graph;

public class CircuitShortDetection {
    public static void main(String args[]) {
        Graph g = new Graph(5); // 假设电路中有5个节点

        // 添加电路中的连接关系，这里简单起见，手动添加几条边
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 0); // 添加一个环路，模拟短路问题

        if (g.isCyclic())
            System.out.println("电路中存在短路问题");
        else
            System.out.println("电路中不存在短路问题");
    }
}
