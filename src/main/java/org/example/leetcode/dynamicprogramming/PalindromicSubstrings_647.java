package org.example.leetcode.dynamicprogramming;

public class PalindromicSubstrings_647 {
    public int countSubstrings(String s) {
        int count = 0;

        for(int i = 0; i < s.length(); i++) {
            count += calculate(s, i, i);
            count += calculate(s, i, i+1);
        }
        return count;
    }

    private int calculate(String s, int l, int r) {
        int count = 0;
        while(l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            count++;
            r++;
            l--;
        }
        return count;
    }
}
