package org.example.leetcode.dynamicprogramming;

public class FibonacciNumber_509 {

    public int fib(int n) {
        //Bottom UP tabulation
        if(n == 0) return 0;
        if(n == 1) return 1;

        int prev = 0;
        int curr = 1;

        for(int i = 2; i < n+1; i++) {
            int temp = curr;;
            curr = curr + prev;
            prev = temp;
        }
        return curr;
    }
    /*
    public int fib(int n) {
            //Bottom UP tabulation
            if(n == 0) return 0;
            if(n == 1) return 1;
            int[] dp = new int[n+1];
            dp[0] = 0;
            dp[1] = 1;

            for(int i = 2; i < n+1; i++) {
                dp[i] = dp[i-2] + dp[i-1];
            }
            return dp[n];
        }
     */
    /*
    public int fib(int n) {
        //Top Down memorization
        Map<Integer, Integer> memo = new HashMap<>();
        memo.put(0, 0);
        memo.put(1, 1);

        return f(n, memo);


    }

    private int f(int x, Map<Integer, Integer> memo) {
        if(memo.containsKey(x)) {
            return memo.get(x);
        } else {
            memo.put(x, f(x-2, memo) + f(x-1, memo));
            return memo.get(x);
        }
    }
     */

    //Recursion
    /*
    public int fib(int n) {
        if(n == 0) return 0;
        if(n == 1) return 1;

        return fib(n-2) + fib(n-1);
    }
     */
}
