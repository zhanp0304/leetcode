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
    LEFT_JOIN("LEFT_JOIN", "左连接"),
    RIGHT_JOIN("RIGHT_JOIN", "左连接"),
    INNER_JOIN("INNER_JOIN", "左连接");

    private final String joinTypeCode;
    private final String joinTypeMeaning;

    JoinTypeEnum(String joinTypeCode, String joinTypeMeaning) {
        this.joinTypeCode = joinTypeCode;
        this.joinTypeMeaning = joinTypeMeaning;
    }

    public String getJoinTypeCode() {
        return joinTypeCode;
    }

    public String getJoinTypeMeaning() {
        return joinTypeMeaning;
    }
}
