package org.zhanp.leetcode.slide_window;

/**
 * LQ3. 无重复字符的最长子串
 *
 * @author zhanpeng
 */
public class LengthOfLongestSubstring {
    public static void main(String[] args) {
        LengthOfLongestSubstring func = new LengthOfLongestSubstring();
        System.out.println(func.lengthOfLongestSubstring("au"));

    }

    public int lengthOfLongestSubstring(String s) {
        if (s == null || "".equals(s)) {
            return 0;
        }
        if (s.length() == 1) {
            return 1;
        }

        int left = 0;
        int right = 0;
        boolean[] existArr = new boolean[128];

        int finalStart = 0;
        int finalRight = 0;

        while (right < s.length()) {
            char character = s.charAt(right);

            if (!existArr[character]) {
                existArr[character] = true;
                right++;
            }

            while (right < s.length() && existArr[s.charAt(right)]) {
                // 当前[left, right]满足要求
                int size = right - 1 - left + 1;
                if (size > (finalRight - finalStart + 1)) {
                    finalStart = left;
                    finalRight = right - 1;
                }
                existArr[s.charAt(left)] = false;
                left++;
            }

            if (right == s.length()) {
                int size = right - 1 - left + 1;
                if (size > (finalRight - finalStart + 1)) {
                    finalStart = left;
                    finalRight = right - 1;
                }
            }
        }

        return finalRight - finalStart + 1;
    }
}
