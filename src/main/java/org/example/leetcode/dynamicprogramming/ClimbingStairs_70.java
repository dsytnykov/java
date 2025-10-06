package org.example.leetcode.dynamicprogramming;

public class ClimbingStairs_70 {
    //Bottom -Up approach
    public int climbStairs(int n) {
        int one = 1;
        int two = 1;

        for(int i = 0; i < n-1; i++) {
            int temp = one;
            one = one + two;
            two = temp;
        }
        return one;
    }

    /*
    def climbStairs(self, n: int) -> int:
        ls = list()
        if n == 0:
            return 1

        for i in range(n+1):
            if i == 0 or i == 1:
                ls.append(1)
            else:
                ls.append(ls[i-2]+ls[i-1])

        return ls[len(ls)-1]
     */

    /* Recursion Python3
    def climbStairs(self, n: int) -> int:
        if n == 0 or n == 1:
            return 1
        return self.climbStairs(n-2) + self.climbStairs(n-1)
     */
}
