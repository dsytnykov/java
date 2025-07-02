package org.example.tasks.ratelimiter;

import java.util.concurrent.*;

/*1️⃣ Implement a Rate Limiter (Leaky Bucket Algorithm)
Create a Rate Limiter that allows a maximum number of requests per second (e.g., 5 requests/second). Use the Leaky Bucket Algorithm, ensuring that:

Excess requests are dropped if the bucket is full.
Requests are processed at a fixed rate.
The implementation is thread-safe.*/
public class RateLimiterTest {
    public static void main(String[] args) throws InterruptedException {
        LeakyBucketRateLimiter rateLimiter = new LeakyBucketRateLimiter(5); // 5 requests/second

        // Simulate 10 requests arriving almost instantly
        for (int i = 1; i <= 10; i++) {
            final int requestId = i;
            boolean accepted = rateLimiter.allowRequest(() ->
                    System.out.println("Processing request " + requestId + " at " + System.currentTimeMillis())
            );
            if (!accepted) {
                System.out.println("Request " + requestId + " dropped!");
            }
            Thread.sleep(100); // Simulating request arrival delay
        }

        Thread.sleep(3000);
        rateLimiter.shutdown();
    }

    private static class LeakyBucketRateLimiter {
        private final BlockingQueue<Runnable> queue;
        private final ScheduledExecutorService leakScheduler;

        public LeakyBucketRateLimiter(int ratePerSecond) {
            this.queue = new LinkedBlockingQueue<>(ratePerSecond);
            this.leakScheduler = Executors.newSingleThreadScheduledExecutor();

            leakScheduler.scheduleAtFixedRate(this::leakRequest, 0, 1000 / ratePerSecond, TimeUnit.MILLISECONDS);
        }

        private void leakRequest() {
            Runnable request = queue.poll();
            if(request != null) {
                request.run();
            }
        }

        public boolean allowRequest(Runnable request) {
            return queue.offer(request);
        }

        public void shutdown() {
            leakScheduler.shutdown();
        }
    }
}
