package org.zhanp.leetcode.slide_window;

import java.util.*;

/**
 * LQ438. 找到字符串中所有字母异位词
 * 给定两个字符串 s 和 p，找到 s 中所有 p 的 异位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
 * <p>
 * 异位词 指由相同字母重排列形成的字符串（包括相同的字符串）。
 * <p>
 */
class FindAnagram {
    public static void main(String[] args) {
        FindAnagram solution = new FindAnagram();
        List<Integer> anagrams = solution.findAnagrams("abacbabc", "abc");
        System.out.println(anagrams);
    }

    public List<Integer> findAnagrams(String s, String p) {
        if (s == null || "".equals(s)) {
            return Collections.emptyList();
        }
        if (p == null || "".equals(p)) {
            return Collections.emptyList();
        }

        int left = 0;
        int right = 0;

        Map<String, Integer> pMap = new HashMap<>(calcBestSize(p.length()));
        for (int i = 0; i < p.length(); i++) {
            String character = String.valueOf(p.charAt(i));
            pMap.put(character, pMap.getOrDefault(character, 0) + 1);
        }

        Set<Integer> results = new HashSet<>(s.length());
        Map<String, Integer> matchCountMap = new HashMap<>(calcBestSize(p.length()));

        int hit = 0;

        while (right < s.length()) {
            for (; right < s.length(); right++) {
                String character = String.valueOf(s.charAt(right));
                if (pMap.get(character) != null) {
                    matchCountMap.put(character, matchCountMap.getOrDefault(character, 0) + 1);
                    if (matchCountMap.getOrDefault(character, 0).equals(pMap.get(character))) {
                        hit++;
                    }
                }
                if (hit >= pMap.size()) {
                    break;
                }
            }
            if (right == s.length()) {
                break;
            }
            for (; left <= right && left < s.length(); left++) {
                if (hit < pMap.size()) {
                    break;
                }
                if (right - left + 1 == p.length()) {
                    // 符合目标子串的长度,加入结果集
                    results.add(left);
                    break;
                }
                String character = String.valueOf(s.charAt(left));
                if (pMap.get(character) != null) {
                    matchCountMap.put(character, matchCountMap.getOrDefault(character, 0) - 1);
                    if (matchCountMap.getOrDefault(character, 0) < pMap.get(character)) {
                        hit--;
                    }
                }
            }
            right++;
        }
        return new ArrayList<>(results);
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