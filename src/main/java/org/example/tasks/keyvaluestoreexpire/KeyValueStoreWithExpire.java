package org.example.tasks.keyvaluestoreexpire;

import java.util.concurrent.*;

/*Implement an In-Memory Key-Value Store with Expiry
Task:
Create an in-memory key-value store that supports:

put(String key, String value, long ttlMillis): Stores a key-value pair with an expiration time.
get(String key): Retrieves the value (or null if expired/not found).
remove(String key): Deletes a key manually.
Requirements:
Use a background thread to clean up expired entries.
Ensure thread safety using ConcurrentHashMap.
Optimize for low memory usage and fast access.*/
public class KeyValueStoreWithExpire {

    private static class InMemoryCache {
        ConcurrentMap<String, String> cache = new ConcurrentHashMap<>();

        private void put(String key, String value, long ttlMillis) {
            cache.put(key, value);

            Runnable runnable = () -> {
                try {
                    Thread.sleep(ttlMillis);
                    cache.remove(key);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            };
            new Thread(runnable).start();
        }

        private String get(String key) {
            return cache.get(key);
        }

        private void remove(String key) {
            cache.remove(key);
            //stop expiration thread
        }
    }

    private static class ExpiringKeyValueStore {
        private final ConcurrentHashMap<String, String> store = new ConcurrentHashMap<>();
        private final ConcurrentHashMap<String, Long> expiryMap = new ConcurrentHashMap<>();
        private final ScheduledExecutorService cleaner = Executors.newScheduledThreadPool(1);

        public ExpiringKeyValueStore() {
            cleaner.scheduleAtFixedRate(this::cleanupExpiredKeys, 1, 1, TimeUnit.SECONDS);
        }

        public void put(String key, String value, long ttlMillis) {
            store.put(key, value);
            expiryMap.put(key, System.currentTimeMillis() + ttlMillis);
        }

        public String get(String key) {
            Long expiryTime = expiryMap.get(key);
            if (expiryTime == null || System.currentTimeMillis() > expiryTime) {
                store.remove(key);
                expiryMap.remove(key);
                return null;
            }
            return store.get(key);
        }

        public void remove(String key) {
            store.remove(key);
            expiryMap.remove(key);
        }

        private void cleanupExpiredKeys() {
            long currentTime = System.currentTimeMillis();
            for (String key : expiryMap.keySet()) {
                if (expiryMap.get(key) < currentTime) {
                    store.remove(key);
                    expiryMap.remove(key);
                }
            }
        }

        public void shutdown() {
            cleaner.shutdown();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        InMemoryCache cache = new InMemoryCache();

        cache.put("1", "2", 1000);
        cache.put("2", "24", 500);
        System.out.println(cache.get("1"));
        System.out.println(cache.get("2"));
        Thread.sleep(600);
        System.out.println(cache.get("1"));
        System.out.println(cache.get("2"));
        Thread.sleep(400);
        System.out.println(cache.get("1"));
        System.out.println(cache.get("2"));
    }
}
