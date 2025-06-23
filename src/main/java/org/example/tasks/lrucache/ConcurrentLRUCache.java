package org.example.tasks.lrucache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class ConcurrentLRUCache<K, V> {
    private final Cache<K, V> cache;

    public ConcurrentLRUCache(int capacity) {
        this.cache = CacheBuilder.newBuilder()
                .maximumSize(capacity)  // Max items before LRU eviction
                .expireAfterAccess(10, TimeUnit.MINUTES) // Optional: Auto-expiry
                .concurrencyLevel(Runtime.getRuntime().availableProcessors()) // Optimized for multi-threading
                .build();
    }

    // Get value (O(1))
    public V get(K key) {
        return cache.getIfPresent(key);
    }

    // Insert/update value (O(1))
    public void put(K key, V value) {
        cache.put(key, value);
    }

    // Print cache contents (for debugging)
    public void printCache() {
        System.out.println(cache.asMap());
    }
}
