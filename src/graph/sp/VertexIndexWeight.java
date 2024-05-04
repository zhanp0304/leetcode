package graph.sp;

import java.util.Comparator;
import java.util.Objects;

/**
 * 中间变量，用于在寻找最短路径中，存放于优先队列，以vertex index为key，通过toEdge的weight做比较
 *
 * @author zhanpeng.jiang 2024/5/4
 */
public class VertexIndexWeight implements Comparable<VertexIndexWeight> {
    private int vertex;
    private double weight;

    public VertexIndexWeight(DirectedEdge directedEdge) {
        this.vertex = directedEdge.to();
        this.weight = directedEdge.weight();
    }

    public VertexIndexWeight(int vertex, double weight) {
        this.vertex = vertex;
        this.weight = weight;
    }

    public int getVertex() {
        return vertex;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VertexIndexWeight that = (VertexIndexWeight) o;
        return vertex == that.vertex;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(vertex);
    }


    @Override
    public int compareTo(VertexIndexWeight o) {
        double diff = weight - o.weight;
        if (diff < 0) {
            return -1;
        } else if (diff > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}
