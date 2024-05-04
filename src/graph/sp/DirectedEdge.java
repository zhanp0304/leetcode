package graph.sp;

/**
 * 有向边
 *
 * @author zhanpeng.jiang 2024/5/4
 */
public class DirectedEdge {
    private int from;
    private int to;
    private double weight;

    public DirectedEdge(int from, int to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public int from() {
        return from;
    }


    public int to() {
        return to;
    }

    public double weight() {
        return weight;
    }

    public String toString() {
        return String.format("%s -> %s, weight: %s", from, to, weight);
    }
}
