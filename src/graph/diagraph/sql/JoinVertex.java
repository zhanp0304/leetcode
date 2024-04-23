package graph.diagraph.sql;

import graph.diagraph.DiGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * 连接顶点
 * TODO：后续在提供接口获取分组顶点的fields给前端时，需要校验前置父节点，只允许有两个
 *
 * @author zhanpeng.jiang 2024/4/22
 */
public class JoinVertex implements DataModelVertex {

    private final int index;

    private final DataModelVertex[] vertices;

    private final JoinTypeEnum joinType;

    private final List<String> onFieldsOfLeftTable;

    private final List<String> onFieldsOfRightTable;

    public JoinVertex(int index, DataModelVertex[] vertices, JoinTypeEnum joinType,
                      List<String> onFieldsOfLeftTable, List<String> onFieldsOfRightTable) {
        this.index = index;
        this.vertices = vertices;
        this.joinType = joinType;
        this.onFieldsOfLeftTable = onFieldsOfLeftTable;
        this.onFieldsOfRightTable = onFieldsOfRightTable;
    }

    @Override
    public int indexOfGraph() {
        return index;
    }

    @Override
    public ModelSqlResult generateSql(DiGraph graph) {
        List<Integer> neighbors = graph.adjacent(index);

        if (neighbors.size() != 1) {
            throw new IllegalArgumentException("Only one neighbor can be connected by vertex of Group-By type");
        }

        ModelSqlResult leftNeighborSql = vertices[neighbors.get(0)].generateSql(graph);
        ModelSqlResult rightNeighborSql = vertices[neighbors.get(1)].generateSql(graph);

        StringBuilder sql = new StringBuilder();

        // TODO： 传递与聚合上一层select fields作为当前的select字段与on 连接字段名

        String leftTableAlias = TableAliasGenerator.generateAlias();
        String rightTableAlias = TableAliasGenerator.generateAlias();
        List<String> leftTableSelectFields = SqlFieldsHelper.getAliasFields(leftNeighborSql.getSelectFields(), leftTableAlias);
        List<String> rightTableSelectFields = SqlFieldsHelper.getAliasFields(rightNeighborSql.getSelectFields(), rightTableAlias);

        List<String> leftTableOnFields = SqlFieldsHelper.getAliasFields(onFieldsOfLeftTable, leftTableAlias);
        List<String> rightTableOnFields = SqlFieldsHelper.getAliasFields(onFieldsOfRightTable, rightTableAlias);

        List<String> andPairs = new ArrayList<>(leftTableOnFields.size());
        for (int i = 0; i < leftTableOnFields.size(); i++) {
            String leftOnField = leftTableOnFields.get(i);
            String rightOnField = rightTableOnFields.get(i);
            if (i == 0) {
                andPairs.add(leftOnField + " = " + rightOnField);
            } else {
                andPairs.add(" AND " + leftOnField + " = " + rightOnField);
            }
        }

        sql.append("SELECT ")
                .append(String.join(", ", leftTableSelectFields))
                .append(",")
                .append(String.join(", ", rightTableSelectFields))
                .append(" FROM ").append("( ").append(leftNeighborSql.getSql()).append(" ) ").append(leftTableAlias)
                .append(joinType.getSqlJoin()).append(" ( ").append(rightNeighborSql.getSql()).append(" ) ").append(rightTableAlias)
                .append("ON ")
                .append(String.join(" ", andPairs));


        // TODO： 暂时不做去除重复列，也不做重复 + "_1" as suffix的操作
        List<String> finalSelectFields = new ArrayList<>(leftNeighborSql.getSelectFields());
        finalSelectFields.addAll(rightNeighborSql.getSelectFields());
        return new ModelSqlResult(sql.toString(), finalSelectFields);
    }
}
