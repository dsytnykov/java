package org.example.leetcode.twopointers;

import java.util.HashSet;
import java.util.Set;

public class LongestSubstringWithoutRepeatingCharacters_3 {
    public int lengthOfLongestSubstring(String s) {
        int l = 0;
        int max = 0;
        Set<Character> set = new HashSet<>();

        for(int r = 0; r < s.length(); r++) {
            while(set.contains(s.charAt(r))) {
                set.remove(s.charAt(l));
                l++;
            }
            max = Math.max(max, r - l + 1);
            set.add(s.charAt(r));
        }
        return max;
    }
}
