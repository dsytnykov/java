package org.example.leetcode;

public class BestTimeToBuyAndSellStockII_122 {
    private static int maxProfitII(int[] prices) {
        int i = 0;
        int n = prices.length;
        int lo = 0;
        int high = 0;
        int result = 0;

        while(i < n - 1) {
            while(i < n - 1 && prices[i] >= prices[i + 1]) {
                i++;
            }
            lo = i;
            while(i < n - 1 && prices[i] <= prices[i + 1]) {
                i++;
            }
            high = i;
            result += prices[high] - prices[lo];
        }
        return result;
    }
}
