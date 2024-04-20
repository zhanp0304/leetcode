package graph.diagraph.arithmetic;

/**
 * Vertx for Arithmetic
 *
 * @author zhanpeng.jiang 2024/4/20
 */
public class ArithmeticVertex {
    private int id;
    private String operationOrValue;

    public ArithmeticVertex(int id, String operationOrValue) {
        this.id = id;
        this.operationOrValue = operationOrValue;
    }

    public int getId() {
        return id;
    }

    public String getOperationOrValue() {
        return operationOrValue;
    }
}
