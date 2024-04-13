package graph;

/**
 * A route that connects starting city and destination city
 *
 * @author zhanpeng.jiang@hand-china.com 2024/1/22
 */
public class DirectRoute {
    String start;
    String dest;

    public DirectRoute(String start, String dest) {
        this.start = start;
        this.dest = dest;
    }

    public String getStart() {
        return start;
    }

    public String getDest() {
        return dest;
    }
}
