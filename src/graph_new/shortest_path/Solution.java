package graph_new.shortest_path;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

class Solution {
    int unProcessedCount;

    public int networkDelayTime(int[][] times, int n, int k) {
        // Build graph
        List<DirectedEdge>[] adjMap = new ArrayList[n + 1];
        int[] distTo = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            adjMap[i] = new ArrayList<>();
            distTo[i] = Integer.MAX_VALUE;
        }
        // Distance to Souce is zero 
        distTo[k] = 0;

        for (int i = 0; i < times.length; i++) {
            int from = times[i][0];
            int to = times[i][1];
            int weight = times[i][2];
            adjMap[from].add(new DirectedEdge(from, to, weight));
        }

        boolean[] marked = new boolean[n + 1];
        this.unProcessedCount = n - 1;
        int maxDelayTime = Integer.MIN_VALUE;
        PriorityQueue<IndexWeight> minPq = new PriorityQueue<>();

        // Dijkstra
        List<DirectedEdge> adjEdges = adjMap[k];
        for (DirectedEdge e : adjEdges) {
            int to = e.to;
            distTo[to] = e.weight;
            minPq.add(new IndexWeight(to, e.weight));
        }

        while (!minPq.isEmpty()) {
            IndexWeight nextVertex = minPq.poll();
            // 每次从最小堆中取出来的值 都是距离s更远的值
            maxDelayTime = nextVertex.weight;
            relax(adjMap, marked, nextVertex.vertex, distTo, minPq);
        }

        if (unProcessedCount > 0) {
            return -1;
        }

        return maxDelayTime;
    }

    private void relax(List<DirectedEdge>[] adjMap, boolean[] marked, int vertex, int[] distTo,
                       PriorityQueue<IndexWeight> minPq) {
        if (!marked[vertex]) {
            marked[vertex] = true;
            --unProcessedCount;
        }

        List<DirectedEdge> edges = adjMap[vertex];
        for (DirectedEdge edge : edges) {
            int to = edge.to;
            int weight = edge.weight;

            if (distTo[to] > (distTo[vertex] + weight)) {
                distTo[to] = distTo[vertex] + weight;

                IndexWeight oldVertex = new IndexWeight(to, weight);
                if (minPq.contains(oldVertex)) {
                    minPq.remove(oldVertex);
                    minPq.add(new IndexWeight(to, distTo[to]));
                } else {
                    minPq.add(new IndexWeight(to, distTo[to]));
                }
            }
        }
    }

    class DirectedEdge {
        public int from;
        public int to;
        public int weight;

        public DirectedEdge(int from, int to, int weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

    class IndexWeight implements Comparable<IndexWeight> {
        public int vertex;
        public int weight;

        public IndexWeight(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        @Override
        public boolean equals(Object item) {
            // 记住，因为我们要实现的是indexMinPq，判断的依据是顶点相同，就认为元素相同，可以被覆盖更新
            return this.vertex == ((IndexWeight) item).vertex;
        }

        @Override
        public int hashCode() {
            return Objects.hash(vertex, weight);
        }

        public int compareTo(IndexWeight another) {
            if (this.weight < another.weight) {
                return -1;
            } else if (this.weight == another.weight) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    public static void main(String[] args) {
        int[][] times = {
                {1, 2, 1},
                {2, 3, 2},
                {1, 3, 4}
        };

        Solution solution = new Solution();
        int i = solution.networkDelayTime(times, 3, 1);
        System.out.println(i);
    }
}