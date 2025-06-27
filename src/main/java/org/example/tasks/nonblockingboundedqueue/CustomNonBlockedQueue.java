package org.example.tasks.nonblockingboundedqueue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;

/*1️⃣ Implement a Non-Blocking Bounded Queue
🔹 Create a thread-safe, lock-free bounded queue using CAS (Compare-And-Swap) operations.
🔹 Use AtomicReference for a linked-list or AtomicIntegerArray for a circular buffer.
🔹 Ensure high performance with minimal contention between producer & consumer threads.*/
public class CustomNonBlockedQueue<T> {
    private final int capacity;
    private final AtomicReferenceArray<T> queue;
    private final AtomicInteger head;
    private final AtomicInteger tail;

    public CustomNonBlockedQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new AtomicReferenceArray<>(capacity);
        this.head = new AtomicInteger(0);
        this.tail = new AtomicInteger(0);
    }

    public boolean enqueue(T item) {
        while(true) {
            int currentTail = tail.get();
            int nextTail = (currentTail + 1) % capacity;
            if(nextTail == head.get()) {
                return false;
            }
            if(queue.compareAndSet(currentTail, null, item)) {
                tail.compareAndSet(currentTail, nextTail);
                return true;
            }
        }
    }

    public T dequeue() {
        while(true) {
            int currentHead = head.get();
            if(currentHead == tail.get()) {
                return null;
            }
            T item = queue.get(currentHead);
            if(item != null && queue.compareAndSet(currentHead, item, null)) {
                head.compareAndSet(currentHead, (currentHead + 1) % capacity);
                return item;
            }
        }
    }
}
