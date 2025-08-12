package org.example.tasks.ratelimiter;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;

public class RateLimiterByUser {
    private final int ratePerSecond;
    private final long windowSizeMillis;
    private final ConcurrentHashMap<String, Deque<Long>> map = new ConcurrentHashMap<>();

    public RateLimiterByUser(int ratePerSecond, long windowSizeMillis) {
        this.ratePerSecond = ratePerSecond;
        this.windowSizeMillis = windowSizeMillis;
    }

    public boolean isRequestAllowed(String userId) {
        Deque<Long> timestamps = map.computeIfAbsent(userId, k -> new ArrayDeque<>());
        long now = System.currentTimeMillis();
        synchronized (timestamps) {
            if(!timestamps.isEmpty() && timestamps.peekFirst() <= now - windowSizeMillis) {
                timestamps.pollFirst();
            }

            if(timestamps.size() < ratePerSecond) {
                timestamps.addLast(now);
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        RateLimiterByUser rateLimiter = new RateLimiterByUser(5, 4000);

        for(int i = 0; i < 10; i++) {
            boolean isValid = rateLimiter.isRequestAllowed("Avrora");
            System.out.println("Request number " + i + " for user Avrora is valid: " + isValid);
            Thread.sleep(500);
        }
    }
}
