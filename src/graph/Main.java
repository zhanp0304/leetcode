package graph;

import java.util.*;

class MyDiGraph {
    private final Map<String, List<String>> adjList;
    private final Set<String> visited;
    private final Stack<String> stack;

    MyDiGraph() {
        adjList = new HashMap<>();
        visited = new HashSet<>();
        stack = new Stack<>();
    }

    void addEdge(String u, String v) {
        adjList.putIfAbsent(u, new ArrayList<>());
        adjList.get(u).add(v);
    }

    boolean isCyclicUtil(String v, Map<String, Boolean> visited, Map<String, Boolean> recStack) {
        if (recStack.getOrDefault(v, false)) {
            return true;
        }
        if (visited.getOrDefault(v, false)) {
            return false;
        }

        visited.put(v, true);
        recStack.put(v, true);

        List<String> neighbors = adjList.getOrDefault(v, new ArrayList<>());
        for (String neighbor : neighbors) {
            if (isCyclicUtil(neighbor, visited, recStack)) {
                return true;
            }
        }

        recStack.put(v, false);
        return false;
    }

    boolean isCyclic() {
        Map<String, Boolean> visited = new HashMap<>();
        Map<String, Boolean> recStack = new HashMap<>();

        for (String node : adjList.keySet()) {
            if (isCyclicUtil(node, visited, recStack)) {
                return true;
            }
        }

        return false;
    }

    List<String> topologicalSort() {
        for (String node : adjList.keySet()) {
            if (!visited.contains(node)) {
                topologicalSortUtil(node);
            }
        }

        List<String> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    private void topologicalSortUtil(String v) {
        visited.add(v);
        List<String> neighbors = adjList.getOrDefault(v, new ArrayList<>());
        for (String neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                topologicalSortUtil(neighbor);
            }
        }
        stack.push(v);
    }
}

public class Main {
    public static void main(String[] args) {
        MyDiGraph graph = new MyDiGraph();
//        graph.addEdge("A", "B");
//        graph.addEdge("B", "C");
//        graph.addEdge("C", "D");
//        graph.addEdge("D", "A");

        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("A", "D");
        graph.addEdge("B", "E");
        graph.addEdge("E", "F");
        graph.addEdge("E", "G");
        graph.addEdge("F", "D");
        graph.addEdge("C", "G");
        graph.addEdge("G", "D");

        if (graph.isCyclic()) {
            System.out.println("图包含环");
        } else {
            System.out.println("图不包含环");
        }

        System.out.println("拓扑排序顺序：" + graph.topologicalSort());

    }
}
