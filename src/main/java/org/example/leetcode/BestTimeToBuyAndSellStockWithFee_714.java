package org.example.leetcode;

public class BestTimeToBuyAndSellStockWithFee_714 {
    public static int maxProfit(int[] prices, int fee) {
        int buy = Integer.MIN_VALUE;
        int sell = 0;

        for(int price : prices) {
            buy = Math.max(buy, sell - price);//profit from previous buy
            sell = Math.max(sell, buy + price - fee);//profit after selling
        }
        return sell;
    }
}
