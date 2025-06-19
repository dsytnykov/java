package org.example.tasks.concurrentexpiredmap;

import java.util.concurrent.*;

public class ConcurrentExpiredMap<K, V> {
    private final long ttl;
    private final ConcurrentMap<K, CacheEntry<V>> ttlCache;
    private final ScheduledExecutorService cleaner;

    public ConcurrentExpiredMap(long ttl, TimeUnit timeUnit) {
        this.ttl = timeUnit.toMillis(ttl);
        ttlCache = new ConcurrentHashMap<>();
        cleaner = Executors.newScheduledThreadPool(1);
        cleaner.scheduleAtFixedRate(this::cleanCache, 1, 1, TimeUnit.MILLISECONDS);
    }

    private void cleanCache() {
        long currentMilliseconds = System.currentTimeMillis();
        ttlCache.forEach((key, cacheEntry) -> {
            if(currentMilliseconds >= cacheEntry.expireTime()) {
                ttlCache.remove(key);
            }
        });
    }

    public void put(K k, V v) {
        ttlCache.put(k, new CacheEntry<>(v, System.currentTimeMillis() + ttl));
    }

    public void remove(K k) {
        ttlCache.remove(k);
    }

    public V get(K k) {
        CacheEntry<V> entry = ttlCache.get(k);
        if(entry == null || entry.isExpired()) {
            ttlCache.remove(k);
            return null;
        }
        return entry.value();
    }

    public void shutdown() {
        cleaner.shutdown();
    }

    public String toString() {
        return ttlCache.toString();
    }

    private record CacheEntry<V>(V value, long expireTime) {
        public boolean isExpired() {
            return System.currentTimeMillis() > expireTime;
        }
    }

    /*private final ConcurrentHashMap<K, CacheEntry<V>> map = new ConcurrentHashMap<>();
    private final ScheduledExecutorService cleaner = Executors.newScheduledThreadPool(1);
    private final long ttlMillis;

    public ConcurrentExpiredMap(long ttl, TimeUnit unit) {
        this.ttlMillis = unit.toMillis(ttl);
        startEvictionTask();
    }

    public void put(K key, V value) {
        map.put(key, new CacheEntry<>(value, System.currentTimeMillis() + ttlMillis));
    }

    public V get(K key) {
        CacheEntry<V> entry = map.get(key);
        if (entry == null || entry.isExpired()) {//Lazy removal if expired
            map.remove(key);
            return null;
        }
        return entry.value;
    }

    public void remove(K key) {
        map.remove(key);
    }

    public boolean containsKey(K key) {
        return get(key) != null;
    }

    private void startEvictionTask() {
        cleaner.scheduleAtFixedRate(() -> {
            long now = System.currentTimeMillis();
            for (K key : map.keySet()) {
                CacheEntry<V> entry = map.get(key);
                if (entry != null && entry.expiryTime <= now) {
                    map.remove(key);
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    public void shutdown() {
        cleaner.shutdown();
    }

    public String toString() {
        return map.toString();
    }

    private record CacheEntry<V>(V value, long expiryTime) {
        boolean isExpired() {
                return System.currentTimeMillis() > expiryTime;
            }
        }*/

}
