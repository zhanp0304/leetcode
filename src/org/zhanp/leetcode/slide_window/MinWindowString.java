package org.zhanp.leetcode.slide_window;

import java.util.*;

/**
 * LQ76. 最小覆盖子串
 * <p>
 * 给你一个字符串 s 、一个字符串 t 。返回 s 中涵盖 t 所有字符的最小子串。如果 s 中不存在涵盖 t 所有字符的子串，则返回空字符串 "" 。
 */
public class MinWindowString {

    public static int successTime = 0;

    public static void main(String[] args) {
        /*
            最小覆盖子串，在S中找出包含T所有字母的最小子串
         */
        final String source = "GABDCBDEBG";
        final String target = "BBG";
        String shortest = slideWindow(source, target);
        System.out.println(shortest);
    }

    public static String findShortest(String source, String target) {


        if (source == null || "".equals(source)) {
            return "";
        }
        if (target == null || "".equals(target)) {
            return "";
        }

        // keyed by target character, valued by character appear count
        Map<String, Long> targetAppearCountMap = new HashMap<>((int) (target.length() / 0.75F + 1));

        for (int i = 0; i < target.length(); i++) {
            char c = target.charAt(i);
            targetAppearCountMap.put(String.valueOf(c), targetAppearCountMap.getOrDefault(String.valueOf(c), 0L) + 1);
        }


        TreeSet<String> resultSet = new TreeSet<>(Comparator.comparingInt(String::length));


        for (int i = 0; i < source.length(); i++) {
            // fail fast
            if ((source.length() - i) < target.length()) {
                break;
            }
            Map<String, Long> sumMatchCountMap = new HashMap<>((int) (target.length() / 0.75F + 1));

            StringBuilder sumString = new StringBuilder();
            for (int j = i; j < source.length(); j++) {
                char c = source.charAt(j);

                String s = String.valueOf(c);
                // 包含s子串，且次数要大于子串已有的个数
                if (targetAppearCountMap.get(s) != null && targetAppearCountMap.get(s).compareTo(sumMatchCountMap.getOrDefault(s, 0L)) > 0) {
                    // 只有满足的才可以append
                    sumString.append(c);
                    sumMatchCountMap.put(s, sumMatchCountMap.getOrDefault(s, 0L) + 1);
                }


                if (sumString.length() >= target.length()) {
                    // 实际上的子串截取[i,j+1)
                    resultSet.add(source.substring(i, j + 1));
                    break;
                }
            }
        }

        System.out.println("resultSet: " + resultSet);

        if (!resultSet.isEmpty()) {
            return resultSet.first();
        }
        return "";
    }

    /**
     * 滑窗
     *
     * @param source
     * @param target
     * @return
     */
    public static String slideWindow(String source, String target) {
        /*
            1. left=0,right=0
            2. targetAppearCountMap初始化。（当允许子串重复出现多次，并且要求次数匹配的时候，就需要用map来记录映射关系）
            3. right++, 直到找到可行解
            4. 若找到可行解，left++, 直到找到[left,right]窗口中的最优解为止。（每次left++,都需要更新最短result和判断是否依然满足解条件）
            5. 重复3,4步骤，先移动right,再移动left,直到right到达右边界为止。
         */
        if (source == null || "".equals(source)) {
            return "";
        }
        if (target == null || "".equals(target)) {
            return "";
        }

        TreeSet<String> resultSet = new TreeSet<>(Comparator.comparingInt(String::length));

        // keyed by targetChar, valued by its appear count
        Map<String, Long> targetAppearCountMap = new HashMap<>(calcBestSize(target.length()));

        // keyed by sourceChar, valued by its appear count
        Map<String, Long> sourceAppearCountMap = new HashMap<>(calcBestSize(source.length()));

        for (int i = 0; i < target.length(); i++) {
            String s = String.valueOf(target.charAt(i));
            targetAppearCountMap.put(s, targetAppearCountMap.getOrDefault(s, 0L) + 1L);
        }

        int left = 0;
        int right = 0;

        // 寻找可行解时，用于判断是否所有字母都存在于字符串T. successTime == target.length
        successTime = 0;
        while (right < source.length()) {
            // 找到当前窗口可行解
            right = findAvailable(source, targetAppearCountMap, sourceAppearCountMap, right);
            if (right == -1) {
                return "";
            }
            // 找到当前窗口最优解
            left = findBest(source, targetAppearCountMap, sourceAppearCountMap, left, right, resultSet);
            right++;
        }
        return resultSet.first();
    }

    private static int findBest(String source, Map<String, Long> targetAppearCountMap, Map<String, Long> sourceAppearCountMap, int left, int right, TreeSet<String> resultSet) {
        for (; left <= right; left++) {
            // compare with result, and update
            final String windowString = source.substring(left, right + 1);
            resultSet.add(windowString);

            // slide left, and find current window best
            String s = String.valueOf(source.charAt(left));
            if (targetAppearCountMap.get(s) != null) {
                long reduceSourceCount = sourceAppearCountMap.get(s) - 1L;
                if (reduceSourceCount < targetAppearCountMap.get(s)) {
                    break;
                }
                sourceAppearCountMap.put(s, sourceAppearCountMap.getOrDefault(s, 0L) - 1L);
            }
        }
        return left;
    }

    /**
     * 找到当前窗口可行解
     *
     * @param source               source
     * @param targetAppearCountMap targetAppearCountMap
     * @param sourceAppearCountMap sourceAppearCountMap
     * @param right                right
     * @return available right
     */
    private static int findAvailable(String source, Map<String, Long> targetAppearCountMap, Map<String, Long> sourceAppearCountMap, int right) {
        for (; right < source.length(); right++) {
            String s = String.valueOf(source.charAt(right));
            if (targetAppearCountMap.containsKey(s)) {
                sourceAppearCountMap.put(s, sourceAppearCountMap.getOrDefault(s, 0L) + 1L);
                // 只有当s在两个map中的出现次数都相等时，successTime才+1
                if (sourceAppearCountMap.get(s).compareTo(targetAppearCountMap.get(s)) == 0) {
                    successTime++;
                }
            }
            // 判断当前窗口，是否已满足可行解
            if (successTime == targetAppearCountMap.size()) {
                // 已找到可行解
                return right;
            }
        }
        return -1;
    }

    /**
     * calc map size
     *
     * @param length length
     * @return bestSize
     */
    public static int calcBestSize(int length) {
        if (length < 3) {
            return length;
        }
        return (int) (length / 0.75F + 1.0F);
    }
}
