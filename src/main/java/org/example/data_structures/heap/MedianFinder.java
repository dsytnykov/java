package org.example.data_structures.heap;

import java.util.PriorityQueue;

//https://leetcode.com/problems/find-median-from-data-stream/description/
public class MedianFinder {
    private final PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
    private final PriorityQueue<Integer> minHeap = new PriorityQueue<>((a, b) -> a - b);

    public void insert(int num) {
        if(maxHeap.isEmpty() || num <= maxHeap.peek()) {
            maxHeap.add(num);
        } else {
            minHeap.add(num);
        }
        balanceHeaps();
    }

    public double findMedian() {
        if(maxHeap.size() == minHeap.size()) {
            return (maxHeap.peek() + minHeap.peek()) / 2.0;
        }
        return maxHeap.peek();
    }

    public void balanceHeaps() {
        if(maxHeap.size() >= minHeap.size() + 1) {
            minHeap.add(maxHeap.poll());
        } else {
            maxHeap.add(minHeap.poll());
        }
    }
}
