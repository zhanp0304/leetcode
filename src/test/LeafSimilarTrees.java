package test;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import static test.TreeNode.initializeTree;

/**
 * Description
 *
 * @author zhanpeng.jiang@hand-china.com 2024/2/20
 */
public class LeafSimilarTrees {
    public static boolean leafSimilar(TreeNode root1, TreeNode root2) {
        Deque<TreeNode> stack1 = new LinkedList<>();
        Deque<TreeNode> stack2 = new LinkedList<>();
        List<Integer> leafs1 = new ArrayList<>();
        List<Integer> leafs2 = new ArrayList<>();

        collectLeafNode(root1, stack1, leafs1);
        collectLeafNode(root2, stack2, leafs2);

        if (leafs1.size() != leafs2.size()) {
            return false;
        }
        for (int i = 0; i < leafs1.size(); i++) {
            int leaf1 = leafs1.get(i);
            int leaf2 = leafs2.get(i);
            if (leaf1 != leaf2) {
                return false;
            }
        }
        return true;
    }

    private static void collectLeafNode(TreeNode root, Deque<TreeNode> stack, List<Integer> leafs) {
        while (root != null || !stack.isEmpty()) {
            // Means that there are available nodes need to be traversed
            while (root != null) {
                // Deep dive into the left and save the last root
                stack.addLast(root);
                root = root.left;
            }

            // Get back to the last root and poll the top element in the stack
            root = stack.pollLast();
            if (root.left == null && root.right == null) {
                leafs.add(root.val);
            }

            /**
             If it's null, it tells the last root that its left children has been traversed in the next loop.
             If it's non-null, it tells that should still traverse its left children in the next loop.
             */
            root = root.right;
        }

        // The stack has been empty and it doesn't have its right children as well
    }
    public static void main(String[] args) {
        Integer[] testData1 = {1,2,3};
        TreeNode root1 = initializeTree(testData1);
        Integer[] testData2 = {1,3,2};
        TreeNode root2 = initializeTree(testData2);
        boolean b = leafSimilar(root1, root2);
        System.out.println(b);

    }
}
