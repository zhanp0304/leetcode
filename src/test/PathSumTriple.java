package test;

import java.util.Deque;
import java.util.LinkedList;

class PathSumTriple {
    private int matchNum = 0;
    private Deque<Integer[]> accumulatePathSumMemo = new LinkedList<>();

    public int pathSum(TreeNode root, int targetSum) {
        dfsExplorePath(root, targetSum);
        return matchNum;
    }

    private void dfsExplorePath(TreeNode root, int targetSum) {
        if (root == null) {
            return;
        }
        updateMemo(root.val);
        accumulatePathSumMemo.offer(new Integer[]{root.val});
        collectIfMatch(targetSum);
        dfsExplorePath(root.left, targetSum);
        dfsExplorePath(root.right, targetSum);
        // Poll for backtrack
        Integer[] pollVal = accumulatePathSumMemo.pollLast();
        assert pollVal != null;
        updateMemoWithPoll(pollVal[0]);
    }

    private void updateMemo(int newVal) {
        for (Integer[] accumulatePathSum : accumulatePathSumMemo) {
            accumulatePathSum[0] += newVal;
        }
    }

    private void updateMemoWithPoll(int pollVal) {
        for (Integer[] accumulatePathSum : accumulatePathSumMemo) {
            accumulatePathSum[0] -= pollVal;
        }
    }

    private void collectIfMatch(int targetSum) {
        for (Integer[] accumulatePathSum : accumulatePathSumMemo) {
            if (accumulatePathSum[0] == targetSum) {
                ++matchNum;
            }
        }
    }

    public static void main(String[] args) {
        PathSumTriple pathSumTriple = new PathSumTriple();
        Integer[] nodes = {10, 5, -3, 3, 2, null, 11, 3, -2, null, 1};
        TreeNode root = TreeNode.initializeTree(nodes);
        int count = pathSumTriple.pathSum(root, 8);
        System.out.println(count);
    }
}