package org.example.tasks.lrucache;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrentLRUCacheTest {


    public static void main(String[] args) throws InterruptedException {
        ConcurrentLRUCache<Integer, String> lruCache = new ConcurrentLRUCache<>(3);

        // Simulating multiple threads accessing the cache
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // Insert elements concurrently
        for (int i = 1; i <= 5; i++) {
            final int key = i;
            executor.execute(() -> {
                lruCache.put(key, "Value" + key);
                System.out.println("Inserted: " + key);
            });
        }

        Thread.sleep(1000); // Allow some processing time

        System.out.println("Cache state after concurrent inserts:");
        lruCache.printCache();

        // Access some elements to change their priority
        lruCache.get(3); // Making 3 recently used
        lruCache.put(6, "Value6"); // This should evict LRU (1)

        System.out.println("Cache state after inserting 6:");
        lruCache.printCache();

        executor.shutdown();
    }
}
