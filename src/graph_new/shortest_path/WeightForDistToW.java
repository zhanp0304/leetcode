package graph_new.shortest_path;

import java.util.Objects;

/**
 * The weight on edge [v -> w]
 *
 * @author zhanpeng.jiang 2024/10/20
 */
public class WeightForDistToW implements Comparable<WeightForDistToW> {
    /**
     * The weight on edge [v -> w]
     */
    public Double weight;
    /**
     * vertex: w
     */
    public Integer vertex;

    public WeightForDistToW(Double weight, Integer vertex) {
        this.weight = weight;
        this.vertex = vertex;
    }


    @Override
    public int compareTo(WeightForDistToW another) {
        if (this.weight < another.weight) {
            return -1;
        } else if (this.weight.compareTo(another.weight) == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WeightForDistToW that = (WeightForDistToW) o;
        return Objects.equals(weight, that.weight) && Objects.equals(vertex, that.vertex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, vertex);
    }
}
