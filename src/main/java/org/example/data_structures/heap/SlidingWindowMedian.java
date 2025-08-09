package org.example.data_structures.heap;

import java.util.PriorityQueue;

//https://leetcode.com/problems/sliding-window-median/description/
public class SlidingWindowMedian {
    private PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
    private PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> a - b);

    public double[] medianSlidingWindow(int[] nums, int k) {
        double[] result = new double[nums.length - k + 1];
        for(int i = 0; i < nums.length; i++) {
            insert(nums[i]);
            if(i - k + 1 >= 0) {
                result[i - k + 1] = findMedian();
                int removedElement = nums[i - k + 1];
                remove(removedElement);
            }
        }
        return result;
    }

    private void remove(int num) {
        if(num <= maxHeap.peek()) {
            maxHeap.remove(num);
        } else {
            minHeap.remove(num);
        }
    }

    private void insert(int num) {
        if(maxHeap.isEmpty() || num <= maxHeap.peek()) {
            maxHeap.add(num);
        } else {
            minHeap.add(num);
        }
        balanceHeaps();
    }

    private double findMedian() {
        if(maxHeap.size() == minHeap.size()) {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
        return maxHeap.peek();
    }

    private void balanceHeaps() {
        if(maxHeap.size() >= minHeap.size() + 1) {
            minHeap.add(maxHeap.poll());
        } else {
            maxHeap.add(minHeap.poll());
        }
    }
}
