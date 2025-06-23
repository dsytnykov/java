package org.example.tasks.lrucache;

/*2️⃣ Implement a Thread-Safe LRU (Least Recently Used) Cache
Design a thread-safe LRU cache with:

A fixed capacity (e.g., 100 entries).
Automatic removal of least recently used items when full.
O(1) time complexity for get() and put() operations.
Efficient multithreading support.
*/
public class LRUCacheTest {

    public static void main(String[] args) {
        LRUCache<Integer, String> lruCache = new LRUCache<>(3);

        lruCache.put(1, "A");
        lruCache.put(2, "B");
        lruCache.put(3, "C");

        System.out.println("Cache after 3 inserts: ");
        lruCache.printCache(); // {1=A, 2=B, 3=C}

        // Access key 1 (makes it most recently used)
        lruCache.get(1);
        lruCache.put(4, "D"); // Evicts least recently used (key 2)

        System.out.println("Cache after accessing 1 and inserting 4: ");
        lruCache.printCache(); // {3=C, 1=A, 4=D}

        // Insert another key, causing another eviction
        lruCache.put(5, "E"); // Evicts key 3

        System.out.println("Cache after inserting 5: ");
        lruCache.printCache(); // {1=A, 4=D, 5=E}
    }
}
