package graph.diagraph.sql;

import graph.diagraph.DepthFirstOrder;
import graph.diagraph.DiGraph;

import java.util.*;

/**
 * 基于DAG的SQL生成器
 * <p>Note: 尝试逆向实现一次X-Pro做的低代码数据建模</p>
 *
 * @author zhanpeng.jiang 2024/4/22
 */
public class DagSqlGenerator {
    private final DiGraph graph;
    private final DataModelVertex[] vertices;

    public DagSqlGenerator(DiGraph graph, DataModelVertex[] dataModelVertices) {
        this.graph = graph;
        this.vertices = dataModelVertices;
    }

    public String generateFinalSql() {
        DepthFirstOrder depthFirstOrder = new DepthFirstOrder(graph);
        int startVertex = depthFirstOrder.reversedPostOrder().next();
        ModelSqlResult sql = vertices[startVertex].generateSql(graph);
        return sql.getSql();
    }

    public static void main(String[] args) {
        DataModelVertex[] vertices = new DataModelVertex[5];

        List<String> selectFields = new ArrayList<>();
        selectFields.add("org_code");
        selectFields.add("total_error_count");
        selectFields.add("org_name");
        CalcVertex calcVertex = new CalcVertex(0, selectFields, vertices);
        vertices[0] = calcVertex;

        List<String> groupFields = new ArrayList<>();
        groupFields.add("org_code");
        groupFields.add("org_name");

        List<String> selectAggregatedFields = new ArrayList<>();
        selectAggregatedFields.add("org_code");
        selectAggregatedFields.add("org_name");

        Map<String, String> aggregateFunctionBySelectField = new HashMap<>();
        aggregateFunctionBySelectField.put("error_count", "sum");

        Map<String, String> aliasMap = Collections.singletonMap("error_count", "total_error_count");
        List<String> aggregateFields = new ArrayList<>();
        aggregateFields.add("error_count");
        GroupByVertex groupByVertex = new GroupByVertex(1, selectAggregatedFields,
                aggregateFields, aggregateFunctionBySelectField, aliasMap, groupFields, vertices);
        vertices[1] = groupByVertex;

        DiGraph graph = new DiGraph(5);
        graph.addEdge(0, 1);

        List<String> leftOnFields = new ArrayList<>();
        leftOnFields.add("org_code");

        List<String> rightOnFields = new ArrayList<>();
        rightOnFields.add("org_code");

        JoinVertex joinVertex = new JoinVertex(2, JoinTypeEnum.LEFT_JOIN,
                leftOnFields, rightOnFields, vertices);
        vertices[2] = joinVertex;
        graph.addEdge(1, 2);

        List<String> inputErrorDetailSelectFields = new ArrayList<>();
        inputErrorDetailSelectFields.add("org_code");
        inputErrorDetailSelectFields.add("error_count");
        InputVertex inputErrorDetailInputVertex = new InputVertex(3, inputErrorDetailSelectFields, "insure_error_detail");
        vertices[3] = inputErrorDetailInputVertex;
        graph.addEdge(2, 3);


        List<String> orgSelectFields = new ArrayList<>();
        orgSelectFields.add("org_code");
        orgSelectFields.add("org_name");
        InputVertex orgVertex = new InputVertex(4, orgSelectFields, "inquiry_org");
        vertices[4] = orgVertex;
        graph.addEdge(2, 4);

        DagSqlGenerator dagSqlGenerator = new DagSqlGenerator(graph, vertices);
        String finalSql = dagSqlGenerator.generateFinalSql();
        System.out.println("finalSql: " + finalSql);
    }
}
