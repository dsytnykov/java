package org.example.tasks.concurrentexpiredmap;

import java.util.concurrent.TimeUnit;

/*5️⃣ Develop a Concurrent Expiring Map (TTL Cache)
🔹 Build a time-to-live (TTL) cache where entries expire after a configurable duration.
🔹 Use background eviction (e.g., scheduled executor) or lazy removal on access.
🔹 Ensure high concurrency and minimal memory overhead with ConcurrentHashMap.*/
public class ConcurrentExpiredMapTest {

    public static void main(String[] args) throws InterruptedException {
        ConcurrentExpiredMap<String, String> cache = new ConcurrentExpiredMap<>(1000, TimeUnit.MILLISECONDS);

        cache.put("1", "1");
        Thread.sleep(500);
        cache.put("2", "2");

        System.out.println(cache);

        Thread.sleep(400);

        System.out.println(cache);

        Thread.sleep(800);
        System.out.println(cache);

        cache.shutdown();
    }
}
