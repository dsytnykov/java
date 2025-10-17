package org.example.leetcode.dynamicprogramming;

public class LongestPalindromicSubstring_5 {
    public String longestPalindrome(String s) {
        int max = 0;
        String maxStr = "";

        for(int i = 0; i < s.length(); i++) {
            int l = i;
            int r = i;
            while(l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
                if (max < r - l + 1) {
                    maxStr = s.substring(l, r + 1);
                    max = r - l + 1;
                }
                l--;
                r++;
            }


            l = i;
            r = i + 1;
            while(l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
                if (max < r - l + 1) {
                    maxStr = s.substring(l, r + 1);
                    max = r - l + 1;
                }
                l--;
                r++;
            }
        }
        return maxStr;
    }
}
