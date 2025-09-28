package org.example.leetcode.twopointers;

public class IsSubsequence_392 {

    public boolean isSubsequence(String s, String t) {
        if(s == null || t == null) return false;
        if(s.isEmpty()) return true;
        int sub = 0;

        for(int i = 0; i < t.length(); i++) {
            if(s.charAt(sub) == t.charAt(i)) {
                sub++;
                if(sub == s.length()) return true;
            }
        }
        return sub == s.length();
    }
}
