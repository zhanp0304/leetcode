package test;

import java.util.LinkedList;

/**
 * Description
 *
 * @author zhanpeng.jiang@hand-china.com 2024/1/20
 */
public class InvertBinaryTree {
    public TreeNode invertTree(TreeNode root) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.addLast(root);

        while (!queue.isEmpty()) {
            TreeNode currentNode = queue.pollFirst();
            if (currentNode.left != null) {
                queue.addLast(currentNode.left);
            }
            if (currentNode.right != null) {
                queue.addLast(currentNode.right);
            }
            swap(currentNode);
        }
        return root;
    }

    private void swap(TreeNode root) {
        TreeNode temp = root.right;
        root.right = root.left;
        root.left = temp;
    }

    public static void main(String[] args) {
        InvertBinaryTree invertBinaryTree = new InvertBinaryTree();
        Integer[] arr = {4, 2, 7, 1, 3, 6, 9};
        TreeNode treeNode = invertBinaryTree.invertTree(TreeNode.initializeTree(arr));
        TreeNode.printTreeASCII(treeNode);
    }
}
