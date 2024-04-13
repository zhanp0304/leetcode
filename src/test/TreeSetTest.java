package test;

import java.util.TreeSet;

/**
 * Description
 *
 * @author zhanpeng.jiang@hand-china.com 2024/3/24
 */
public class TreeSetTest {
    public static void main(String[] args) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(1);
        treeSet.add(3);
        treeSet.add(5);
        treeSet.add(2);
        treeSet.add(4);
        System.out.println(treeSet);
    }
}
