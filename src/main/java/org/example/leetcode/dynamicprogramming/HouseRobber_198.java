package org.example.leetcode.dynamicprogramming;

public class HouseRobber_198 {
    //Bottom-Up approach without dp array
    public int rob(int[] nums) {
        int rob1 = 0;
        int rob2 = 0;

        for(int i = 0; i < nums.length; i++ ) {
            int temp = Math.max(rob1 + nums[i], rob2);
            rob1 = rob2;
            rob2 = temp;
        }
        return rob2;
    }

    /* Top-Down approach
    int[] memo;
    public int rob(int[] nums) {
        memo = new int[nums.length + 1];
        Arrays.fill(memo, -1);

        return rob(nums, nums.length - 1);
    }

    private int rob(int[] nums, int i) {
        if(i < 0) return 0;
        if(memo[i] >= 0) return memo[i];

        memo[i] = Math.max(rob(nums, i-2) + nums[i], rob(nums, i-1));
        return memo[i];
    }
     */

    /* Recursion
    public int rob(int[] nums) {
        return rob(nums, nums.length - 1);
    }

    private int rob(int[] nums, int i) {
        if(i < 0) return 0;

        return Math.max(rob(nums, i-2) + nums[i], rob(nums, i-1));
    }
     */
}
