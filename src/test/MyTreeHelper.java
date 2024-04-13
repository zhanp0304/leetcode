package test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;

/**
 * Description
 *
 * @author zhanpeng.jiang@hand-china.com 2024/3/2
 */
public class MyTreeHelper {
    // Build Tree by nums with level order
    public static TreeNode buildTree(Integer[] nums, Deque<TreeNode> queue) {
        if (nums.length <= 0) {
            return null;
        }
        TreeNode root = new TreeNode(nums[0]);
        queue.offer(root);

        int i = 0;
        while (i < nums.length) {
            TreeNode node = queue.poll();
            assert node != null;
            if (++i < nums.length) {
                if (nums[i] != null) {
                    node.left = new TreeNode(nums[i]);
                    queue.offer(node.left);
                } else {
                    node.left = null;
                }
            }
            if (++i < nums.length) {
                if (nums[i] != null) {
                    node.right = new TreeNode(nums[i]);
                    queue.offer(node.right);
                } else {
                    node.right = null;
                }
            }
        }
        return root;
    }

    public static void main(String[] args) {
//        Integer[] nums = {3, 2, 3, 1, 2, 4, 5, 5, 6};
        Integer[] nums = {3, 2, 3, null, 4, null, null, 5, 6};
        Deque<TreeNode> queue = new ArrayDeque<>();
        TreeNode root = buildTree(nums, queue);
        TreeNode.printTreeASCII(root);

        PriorityQueue<Integer> integers = new PriorityQueue<>(5, (k1, k2) -> {
            return -1;
        });
    }
}
