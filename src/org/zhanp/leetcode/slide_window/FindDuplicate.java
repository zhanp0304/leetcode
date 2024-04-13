package org.zhanp.leetcode.slide_window;

/**
 * LQ219. 寻找重复元素 ii
 */
public class FindDuplicate {
    public static void main(String[] args) {
        FindDuplicate findDuplicate = new FindDuplicate();
        int nums[] = {1, 2, 3, 1, 2, 3};
        boolean b = findDuplicate.containsNearbyDuplicate(nums, 2);
        System.out.println(b);
    }

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        if (nums.length == 0) {
            return false;
        }

        if (nums.length == 1) {
            return false;
        }

        int left = 0;
        int right = 1;
        return true;
    }
}
