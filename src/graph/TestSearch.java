package graph;

import java.util.Scanner;

/**
 * 测试 {@link DepthFirstSearch}
 *
 * @author zhanpeng.jiang 2024/4/14
 */
public class TestSearch {
    public static void main(String[] args) {
        MySimpleGraph graph = new MySimpleGraph(9);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(0, 5);
        graph.addEdge(0, 6);
        graph.addEdge(5, 3);
        graph.addEdge(5, 4);
        graph.addEdge(3, 4);
        graph.addEdge(4, 6);
        graph.addEdge(7, 8);
//        graph.addEdge(6, 7);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            int s = scanner.nextInt();
            DepthFirstSearch search = new DepthFirstSearch(graph, s);
            // 输出从s出发，能经过哪些节点
            for (int i = 0; i < graph.V(); i++) {
                if (search.marked(i)) {
                    System.out.print(i + " ");
                }
            }
            System.out.println();

            if (search.count() == graph.V()) {
                System.out.println("Connected");
            } else {
                System.out.println("Not connected");
            }
        }
    }}
