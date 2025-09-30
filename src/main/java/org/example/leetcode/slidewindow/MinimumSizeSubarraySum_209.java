package org.example.leetcode.slidewindow;

public class MinimumSizeSubarraySum_209 {

    public int minSubArrayLen(int target, int[] nums) {
        int sum = 0;
        int min_size = Integer.MAX_VALUE;
        int j = 0;

        for(int i = 0; i < nums.length; i++) {
            if(target <= nums[i]) return 1;
            sum += nums[i];
            while(sum >= target && j <= i) {
                min_size = Math.min(min_size, i - j + 1 );
                sum -= nums[j];
                j++;
            }
        }
        return min_size == Integer.MAX_VALUE ? 0 : min_size;
    }
}
