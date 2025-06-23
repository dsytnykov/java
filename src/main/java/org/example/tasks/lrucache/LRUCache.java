package org.example.tasks.lrucache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> {
    private final int capacity;
    private final LinkedHashMap<K, V> cache;
    private final Object lock = new Object();

    public LRUCache(int capacity) {
        this.capacity = capacity;
        cache = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry (Map.Entry<K,V> eldest) {
                return size() > LRUCache.this.capacity;
            }
        };
    }

    public V get(K key) {
        synchronized (lock) {
            return cache.getOrDefault(key, null);
        }
    }

    public void put (K key, V value) {
        synchronized (lock) {
            cache.put(key, value);
        }
    }

    //for debugging
    public void printCache() {
        synchronized (lock) {
            System.out.println(cache);
        }
    }
}
