package org.example.leetcode.dynamicprogramming;

public class MinCostClimbingStairs_746 {
    public int minCostClimbingStairs(int[] cost) {

        for(int i = cost.length - 2; i >= 0; i--) {
            cost[i] += Math.min(cost[i+1], i+2 >= cost.length ? 0: cost[i+2] );
        }

        return Math.min(cost[0], cost[1]);
    }
}
