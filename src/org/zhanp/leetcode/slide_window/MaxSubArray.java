package org.zhanp.leetcode.slide_window;

import java.util.HashMap;
import java.util.Map;

/**
 * 剑指 Offer 42. 连续子数组的最大和
 * <p>
 * 输入一个整型数组，数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。
 * <p>
 * 要求时间复杂度为O(n)。
 * <p>
 * 输入: nums = [-2,1,-3,4,-1,2,1,-5,4]
 * 输出: 6
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 *
 * @author zhanpeng
 */
public class MaxSubArray {

    public static void main(String[] args) {
        MaxSubArray maxSubArray = new MaxSubArray();
        int[] nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(maxSubArray.maxSubArray(nums));
    }

    public int maxSubArray(int[] nums) {
        int left;
        int right = 0;
        int maxSum = Integer.MIN_VALUE;

        Map<Integer, Integer> idxMaxSumMap = new HashMap<>(nums.length);

        while (right < nums.length) {
            left = right - 1;
            int currentSum;
            if (idxMaxSumMap.get(left) == null) {
                idxMaxSumMap.put(right, nums[right]);
                currentSum = nums[right];
            } else {
                // 用上一个滑窗的最大值与当前值，做累加比较，然后计算出当前滑窗的最大值
                currentSum = Math.max(idxMaxSumMap.get(left) + nums[right], nums[right]);
                idxMaxSumMap.put(right, currentSum);
            }
            if (currentSum > maxSum) {
                maxSum = currentSum;
            }
            right++;
        }
        return maxSum;
    }
}
