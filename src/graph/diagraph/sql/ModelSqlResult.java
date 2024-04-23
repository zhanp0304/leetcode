package graph.diagraph.sql;

import java.util.List;

/**
 * ModelSqlResult
 *
 * @author zhanpeng.jiang 2024/4/23
 */
public class ModelSqlResult {
    /**
     * Vertex执行完generateSql方法后生成的sql
     */
    private String sql;

    private List<String> selectFields;

    public ModelSqlResult(String sql, List<String> selectFields) {
        this.sql = sql;
        this.selectFields = selectFields;
    }

    public String getSql() {
        return sql;
    }

    public List<String> getSelectFields() {
        return selectFields;
    }
}
