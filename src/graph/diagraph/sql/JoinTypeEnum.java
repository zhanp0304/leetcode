package graph.diagraph.sql;

/**
 * JoinTypeEnum
 *
 * @author zhanpeng.jiang 2024/4/22
 */
public enum JoinTypeEnum {
    /**
     * 表与表的连接类型
     */
    LEFT_JOIN("LEFT JOIN", "左连接"),
    RIGHT_JOIN("RIGHT JOIN", "右连接"),
    INNER_JOIN("INNER JOIN", "内连接");

    private final String sqlJoin;
    private final String joinTypeMeaning;

    JoinTypeEnum(String sqlJoin, String joinTypeMeaning) {
        this.sqlJoin = sqlJoin;
        this.joinTypeMeaning = joinTypeMeaning;
    }

    public String getSqlJoin() {
        return sqlJoin;
    }

    public String getJoinTypeMeaning() {
        return joinTypeMeaning;
    }
}
