package graph.sql;

import java.util.*;

// Define different types of nodes
interface Node {
    String generateSQL();
}

class InputNode implements Node {
    private String tableName;
    private String alias;
    private List<String> fieldNames;

    public InputNode(String tableName, String... fieldNames) {
        this.tableName = tableName;
        this.alias = System.nanoTime() + UUID.randomUUID().toString().replace("-", "").substring(0, 5);
        this.fieldNames = Arrays.asList(fieldNames);
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public String generateSQL() {
        return String.format("SELECT %s FROM %s AS %s", Concatenator.concat(fieldNames), tableName, alias);
    }
}

class JoinNode implements Node {
    private String joinType;
    private Node leftNode;
    private Node rightNode;
    private String condition;

    public JoinNode(String joinType, Node leftNode, Node rightNode, String condition) {
        this.joinType = joinType;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.condition = condition;
    }

    @Override
    public String generateSQL() {
        return "FROM (" + leftNode.generateSQL() + " ) "
                + joinType + " JOIN " + rightNode.generateSQL() + " ON " + condition;
    }
}

class CalculateNode implements Node {
    private List<String> selectFields;

    public CalculateNode(List<String> selectFields) {
        this.selectFields = selectFields;
    }

    @Override
    public String generateSQL() {
        return "SELECT " + String.join(", ", selectFields);
    }
}

// DAG implementation
class DAG {
    private Map<Node, List<Node>> adjacencyList;

    public DAG() {
        adjacencyList = new HashMap<>();
    }

    public void addNode(Node node, List<Node> dependencies) {
        adjacencyList.put(node, dependencies);
    }

    // Topological sort to traverse the DAG
    private List<Node> topologicalSort() {
        Stack<Node> stack = new Stack<>();
        Set<Node> visited = new HashSet<>();

        for (Node node : adjacencyList.keySet()) {
            if (!visited.contains(node)) {
                topologicalSortUtil(node, visited, stack);
            }
        }

        List<Node> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    private void topologicalSortUtil(Node node, Set<Node> visited, Stack<Node> stack) {
        visited.add(node);
        List<Node> dependencies = adjacencyList.getOrDefault(node, Collections.emptyList());
        for (Node dependent : dependencies) {
            if (!visited.contains(dependent)) {
                topologicalSortUtil(dependent, visited, stack);
            }
        }
        stack.push(node);
    }

    // Generate SQL from the DAG
    public String generateSQL() {
        List<Node> sortedNodes = topologicalSort();
        StringBuilder sqlBuilder = new StringBuilder();

        for (Node node : sortedNodes) {
            if (node instanceof InputNode) {
                continue;
            }
            sqlBuilder.append(node.generateSQL()).append("\n");
        }

        return sqlBuilder.toString();
    }
}

public class Main {
    public static void main(String[] args) {
        // Create nodes
        InputNode input1 = new InputNode("error_detail", "code", "name");
        InputNode input2 = new InputNode("error_action", "code", "status");

        JoinNode joinNode = new JoinNode("LEFT", input1, input2, String.format("%s.code = %s.code", input1.getAlias(), input2.getAlias()));
        CalculateNode calculateNode = new CalculateNode(Arrays.asList(input1.getAlias() + ".name", input2.getAlias() + ".status"));

        // Create DAG and add nodes
        DAG dag = new DAG();
        dag.addNode(input1, Collections.emptyList());
        dag.addNode(input2, Collections.emptyList());
        dag.addNode(joinNode, Arrays.asList(input1, input2));
        dag.addNode(calculateNode, Collections.singletonList(joinNode));

        // Generate SQL
        String sql = dag.generateSQL();
        System.out.println(sql);
    }
}
