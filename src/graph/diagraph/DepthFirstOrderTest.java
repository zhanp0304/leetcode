package graph.diagraph;

import java.util.Iterator;

import static org.junit.gen5.api.Assertions.assertEquals;

/**
 * DepthFirstOrderTest
 *
 * @author zhanpeng.jiang 2024/4/20
 */
public class DepthFirstOrderTest {
    public static void main(String[] args) {
        testDepthFirstOrder();
    }

    private static void testDepthFirstOrder() {
        DiGraph graph = new DiGraph(7);
        graph.addEdge(0, 5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 6);
        graph.addEdge(3, 5);
        graph.addEdge(2, 3);
        graph.addEdge(2, 0);
        graph.addEdge(5, 4);

        DepthFirstOrder depthFirstOrder = new DepthFirstOrder(graph);
        // Test preOrder
        Iterator<Integer> preOrderIterator = depthFirstOrder.preOrder();
        assertEquals("0 5 4 1 6 2 3", traverseToString(preOrderIterator), "Pre-order traversal");

        // Test postOrder
        Iterator<Integer> postOrderIterator = depthFirstOrder.postOrder();
        assertEquals("4 5 1 6 0 3 2", traverseToString(postOrderIterator), "Post-order traversal");

        // Test reversedPostOrder
        Iterator<Integer> reversedPostOrderIterator = depthFirstOrder.reversedPostOrder();
        assertEquals("2 3 0 6 1 5 4", traverseToString(reversedPostOrderIterator), "Reversed post-order traversal");

        System.out.println("All passed");
    }

    private static String traverseToString(Iterator<Integer> iterator) {
        StringBuilder builder = new StringBuilder();
        while (iterator.hasNext()) {
            builder.append(iterator.next()).append(" ");
        }
        return builder.toString().trim();
    }
}
