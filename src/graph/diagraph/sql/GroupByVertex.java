package graph.diagraph.sql;

import graph.diagraph.DiGraph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分组顶点
 * TODO：后续在提供接口获取分组顶点的fields给前端时，需要校验前置父节点，只允许有一个
 *
 * @author zhanpeng.jiang 2024/4/22
 */
public class GroupByVertex implements DataModelVertex {

    private final int index;

    private final List<String> selectFields;

    private final List<String> aggregateFields;

    private final Map<String, String> aggregateFunctionBySelectField;

    private final Map<String, String> aliasFieldByOriginField;

    private final List<String> groupByFields;

    private final DataModelVertex[] vertices;

    public GroupByVertex(int index, List<String> selectFields,
                         List<String> aggregateFields, Map<String, String> aggregateFunctionBySelectField,
                         Map<String, String> aliasFieldByOriginField,
                         List<String> groupByFields, DataModelVertex[] vertices) {
        this.index = index;
        this.selectFields = selectFields;
        this.aggregateFields = aggregateFields;
        this.aggregateFunctionBySelectField = aggregateFunctionBySelectField;
        this.aliasFieldByOriginField = aliasFieldByOriginField;
        this.groupByFields = groupByFields;
        this.vertices = vertices;
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
        ModelSqlResult modelSqlResult = vertices[neighbors.get(0)].generateSql(graph);

        StringBuilder sql = new StringBuilder();

        Map<Integer, String> needAroundWithAggregateFuncMap = new HashMap<>();
        for (int i = 0; i < aggregateFields.size(); i++) {
            String aggregateField = aggregateFields.get(i);
            String aggregateFunc = aggregateFunctionBySelectField.get(aggregateField);
            if (aggregateFunc != null) {
                needAroundWithAggregateFuncMap.put(i, aggregateFunc);
            }
        }
        // FIXME： 聚合函数的alias丢失

        // TODO： alias需要重新设计，有点赶着看效果和实现MVP了
        String alias = TableAliasGenerator.generateAlias();
        List<String> aliasFields = SqlFieldsHelper.assembleFields(selectFields, alias, aliasFieldByOriginField, true);

        for (int i = 0; i < aggregateFields.size(); i++) {
            String aggregateFunc = needAroundWithAggregateFuncMap.get(i);
            if (aggregateFunc != null) {
                aggregateFields.set(i, aggregateFunc + "(" + aggregateFields.get(i) + ") AS " + aliasFieldByOriginField.get(aggregateFields.get(i)));
            }
        }

        List<String> aliasGroupByFields = SqlFieldsHelper.assembleFields(groupByFields, alias, aliasFieldByOriginField, false);

        sql.append("SELECT ")
                .append(String.join(", ", aliasFields))
                .append(",").append(String.join(", ", aggregateFields))
                .append(" FROM ").append("( ").append(modelSqlResult.getSql()).append(" )")
                .append(" ").append(alias)
                .append(" GROUP BY ").append(String.join(",", aliasGroupByFields));

        return new ModelSqlResult(sql.toString(), selectFields);
    }
}
