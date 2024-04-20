package graph.diagraph.arithmetic;

import graph.diagraph.DiaGraph;

import java.util.ArrayList;
import java.util.List;

/**
 * 基于DAG画布生成的算术表达式，通过构建DAG，解析用户拖拉拽生成的DAG图，最终计算出表达式结果
 *
 * @author zhanpeng.jiang 2024/4/20
 */
public class DagArithmeticCalculator {
    public static void main(String[] args) {
        /**
         * Digraph 1:
         * 2 + (3 * 4) + (3 * 4) / 5
         *
         *      +
         *     / \
         *    v  v
         *   +   /(DIV)
         *   /\  / \
         *  v v  v v
         *  2  *   5
         *     /\
         *    v v
         *    3 4
         */
        // All vertex in DAG shown as above example
        String[] values = new String[]{"+", "+", "2", "*", "3", "4", "/", "5"};
        List<ArithmeticVertex> vertexes = new ArrayList<>();
        vertexes.add(new ArithmeticVertex(0, "+"));
        vertexes.add(new ArithmeticVertex(1, "+"));
        vertexes.add(new ArithmeticVertex(2, "2"));
        vertexes.add(new ArithmeticVertex(3, "*"));
        vertexes.add(new ArithmeticVertex(4, "3"));
        vertexes.add(new ArithmeticVertex(5, "4"));
        vertexes.add(new ArithmeticVertex(6, "/"));
        vertexes.add(new ArithmeticVertex(7, "5"));


        // Built DAG graph
        DiaGraph dag = new DiaGraph(values.length);
        // Say we already know how to connect the vertex （Possibly get them by front-end)
        dag.addEdge(0, 1);
        dag.addEdge(0, 6);
        dag.addEdge(1, 2);
        dag.addEdge(1, 3);
        dag.addEdge(3, 4);
        dag.addEdge(3, 5);
        dag.addEdge(6, 3);
        dag.addEdge(6, 7);

        // 
    }
}
