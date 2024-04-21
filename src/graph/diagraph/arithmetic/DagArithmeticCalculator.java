package graph.diagraph.arithmetic;

import graph.diagraph.DepthFirstOrder;
import graph.diagraph.DiGraph;
import graph.diagraph.DirectedCycle;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.gen5.api.Assertions.assertEquals;

/**
 * 基于DAG画布生成的算术表达式，通过构建DAG，解析用户拖拉拽生成的DAG图，最终计算出表达式结果
 *
 * @author zhanpeng.jiang 2024/4/20ß
 */
public class DagArithmeticCalculator {
    private final boolean[] visited;
    private final double[] buffer;
    private final DiGraph graph;
    private final String[] values;


    public DagArithmeticCalculator(String[] values, DiGraph graph) {
        this.visited = new boolean[values.length];
        this.graph = graph;
        this.values = values;
        this.buffer = new double[values.length];
    }

    public double getFinalValue() {
        // Check Cycle
        DirectedCycle directedCycle = new DirectedCycle(graph);
        if (directedCycle.hasCycle()) {
            throw new IllegalArgumentException("The graph should not has any cycle within it");
        }

        // Topological sort to pick up the start vertex
        DepthFirstOrder depthFirstOrder = new DepthFirstOrder(graph);
        Iterator<Integer> topologicalOrder = depthFirstOrder.reversedPostOrder();

        // Dfs calculation
        int startVertex = topologicalOrder.next();
        return dfsCalc(startVertex);
    }

    private double dfsCalc(int vertex) {
        visited[vertex] = true;
        String currentText = values[vertex];
        if (isDigit(currentText)) {
            buffer[vertex] = Double.parseDouble(currentText);
            return buffer[vertex];
        }

        double value = Integer.MIN_VALUE;

        if ("+".equals(currentText)) {
            value = plusCalculation(vertex);
        } else if ("-".equals(currentText)) {
            value = subtraction(vertex, value);
        } else if ("*".equals(currentText)) {
            value = multiplyCalculation(vertex);
        } else if ("/".equals(currentText)) {
            value = divideCalculation(vertex, value);
        } else {
            throw new IllegalArgumentException(String.format("The operation[%s] has not supported yet", currentText));
        }

        buffer[vertex] = value;
        return value;
    }

    private double divideCalculation(int vertex, double value) {
        List<Integer> adjacent = graph.adjacent(vertex);
        for (int i = 0; i < adjacent.size(); i++) {
            int neighbor = adjacent.get(i);
            if (i == 0) {
                value = dfsCalcOrGetBuffer(neighbor);
            } else {
                value /= dfsCalcOrGetBuffer(neighbor);
            }
        }
        return value;
    }

    private double multiplyCalculation(int vertex) {
        double value;
        value = 1;
        for (int neighbor : graph.adjacent(vertex)) {
            value *= dfsCalcOrGetBuffer(neighbor);
        }
        return value;
    }

    private double subtraction(int vertex, double value) {
        List<Integer> adjacent = graph.adjacent(vertex);
        for (int i = 0; i < adjacent.size(); i++) {
            int neighbor = adjacent.get(i);
            if (i == 0) {
                value = dfsCalcOrGetBuffer(neighbor);
            } else {
                value -= dfsCalcOrGetBuffer(neighbor);
            }
        }
        return value;
    }

    private double plusCalculation(int vertex) {
        double value = 0.00;
        for (int neighbor : graph.adjacent(vertex)) {
            value += dfsCalcOrGetBuffer(neighbor);
        }
        return value;
    }

    private double dfsCalcOrGetBuffer(int neighbor) {
        if (visited[neighbor]) {
            System.out.printf("Buffer Cache and return '%s'\n", buffer[neighbor]);
            return buffer[neighbor];
        }
        return dfsCalc(neighbor);
    }

    private boolean isDigit(String text) {
        return Stream.of("+", "-", "*", "/").noneMatch(s -> s.equals(text));
    }

    public static void main(String[] args) {
        /**
         * Digraph 1:
         * 2 + (3 * 4) + (3 * 4) / 5
         *
         *      +
         *     / \
         *    v  v
         *   +   /(DIV)
         *   /\  / \
         *  v v  v v
         *  2  *   5
         *     /\
         *    v v
         *    3 4
         */
        // All vertex in DAG shown as above example
        String[] values = new String[]{"+", "+", "2", "*", "3", "4", "/", "5"};

        // Built DAG graph
        DiGraph graph = new DiGraph(values.length);
        // Say we already know how to connect the vertex （Possibly get them by front-end)
        graph.addEdge(0, 1);
        graph.addEdge(0, 6);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(3, 4);
        graph.addEdge(3, 5);
        graph.addEdge(6, 3);
        graph.addEdge(6, 7);

        DagArithmeticCalculator calculator = new DagArithmeticCalculator(values, graph);
        double finalValue = calculator.getFinalValue();
        assertEquals(16.4, finalValue, "DAG Arithmetic Calculation");
        System.out.println("Bingo!");
    }

}
