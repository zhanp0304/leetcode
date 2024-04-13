package test;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

class Solution {
    private List<List<Integer>> res;
    private int sum;

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        this.res = new ArrayList<>();
        Deque<Integer> queue = new LinkedList<>();
        dfs(candidates, queue, 0, target);
        return res;
    }

    private void dfs(int[] candidates, Deque<Integer> queue, int start, int target) {
        if (sum == target) {
            res.add(new ArrayList<>(queue));
            return;
        }

        if (sum > target) {
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            queue.offer(candidates[i]);
            sum += candidates[i];

            dfs(candidates, queue, i, target); // Note the change from i to i + 1
            queue.pollLast();
            sum -= candidates[i];
        }
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        int[] ints = {2, 3, 6, 7};
        List<List<Integer>> lists = solution.combinationSum(ints, 7);
        System.out.println(lists);
    }
}
