package graph.diagraph;

public class DirectedCycleTest {
    public static void main(String[] args) {
        testSpecialGraph();
        testEmptyGraph();
        testSingleNodeGraph();
        testNoEdgesGraph();
        testCycleInSmallGraph();
        testNoCycleInSmallGraph();
        testCycleInLargeGraph();
        testNoCycleInLargeGraph();
    }

    private static void testSpecialGraph() {
        DiaGraph graph = new DiaGraph(3);
        graph.addEdge(0, 1);
        graph.addEdge(2, 1);
        DirectedCycle cycleDetector = new DirectedCycle(graph);
        assert !cycleDetector.hasCycle() : "Test failed: graph should not have a cycle.";
        System.out.println("Test passed: graph has no cycle.");
    }

    private static void testEmptyGraph() {
        DiaGraph emptyGraph = new DiaGraph(0);
        DirectedCycle cycleDetector = new DirectedCycle(emptyGraph);
        assert !cycleDetector.hasCycle() : "Test failed: Empty graph should not have a cycle.";
        System.out.println("Test passed: Empty graph has no cycle.");
    }

    private static void testSingleNodeGraph() {
        DiaGraph singleNodeGraph = new DiaGraph(1);
        DirectedCycle cycleDetector = new DirectedCycle(singleNodeGraph);
        assert !cycleDetector.hasCycle() : "Test failed: Single node graph should not have a cycle.";
        System.out.println("Test passed: Single node graph has no cycle.");
    }

    private static void testNoEdgesGraph() {
        DiaGraph noEdgesGraph = new DiaGraph(4);
        DirectedCycle cycleDetector = new DirectedCycle(noEdgesGraph);
        assert !cycleDetector.hasCycle() : "Test failed: Graph with no edges should not have a cycle.";
        System.out.println("Test passed: Graph with no edges has no cycle.");
    }

    private static void testCycleInSmallGraph() {
        DiaGraph smallGraphWithCycle = new DiaGraph(3);
        smallGraphWithCycle.addEdge(0, 1);
        smallGraphWithCycle.addEdge(1, 2);
        smallGraphWithCycle.addEdge(2, 0); // Creating a cycle
        DirectedCycle cycleDetector = new DirectedCycle(smallGraphWithCycle);
        assert cycleDetector.hasCycle() : "Test failed: Small graph with cycle should have a cycle.";
        System.out.println("Test passed: Small graph with cycle has a cycle.");
    }

    private static void testNoCycleInSmallGraph() {
        DiaGraph smallGraphNoCycle = new DiaGraph(4);
        smallGraphNoCycle.addEdge(0, 1);
        smallGraphNoCycle.addEdge(1, 2);
        smallGraphNoCycle.addEdge(2, 3); // No cycle present
        DirectedCycle cycleDetector = new DirectedCycle(smallGraphNoCycle);
        assert !cycleDetector.hasCycle() : "Test failed: Small graph with no cycle should not have a cycle.";
        System.out.println("Test passed: Small graph with no cycle has no cycle.");
    }

    private static void testCycleInLargeGraph() {
        DiaGraph largeGraphWithCycle = new DiaGraph(6);
        largeGraphWithCycle.addEdge(0, 1);
        largeGraphWithCycle.addEdge(1, 2);
        largeGraphWithCycle.addEdge(2, 3);
        largeGraphWithCycle.addEdge(3, 4);
        largeGraphWithCycle.addEdge(4, 5);
        largeGraphWithCycle.addEdge(5, 0); // Creating a cycle
        DirectedCycle cycleDetector = new DirectedCycle(largeGraphWithCycle);
        assert cycleDetector.hasCycle() : "Test failed: Large graph with cycle should have a cycle.";
        System.out.println("Test passed: Large graph with cycle has a cycle.");
    }

    private static void testNoCycleInLargeGraph() {
        DiaGraph largeGraphNoCycle = new DiaGraph(8);
        largeGraphNoCycle.addEdge(0, 1);
        largeGraphNoCycle.addEdge(1, 2);
        largeGraphNoCycle.addEdge(2, 3);
        largeGraphNoCycle.addEdge(3, 4);
        largeGraphNoCycle.addEdge(4, 5);
        largeGraphNoCycle.addEdge(5, 6);
        largeGraphNoCycle.addEdge(6, 7); // No cycle present
        DirectedCycle cycleDetector = new DirectedCycle(largeGraphNoCycle);
        assert !cycleDetector.hasCycle() : "Test failed: Large graph with no cycle should not have a cycle.";
        System.out.println("Test passed: Large graph with no cycle has no cycle.");
    }
}
