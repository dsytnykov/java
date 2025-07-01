package org.example.tasks.prodconscustomqueue;

import java.util.ArrayList;
import java.util.List;

/*Implement a Custom BlockingQueue (Producer-Consumer)
Task:
Create a custom BlockingQueue<T> implementation that supports:

enqueue(T item): Adds an item to the queue (blocks if full).
dequeue(): Removes and returns an item (blocks if empty).
Requirements:
Use wait()/notify() for synchronization.
Implement a bounded queue with a fixed size.
Demonstrate multiple producer-consumer threads using this queue.*/
public class ProduceConsumerCustomQueue {

    private static class CustomQueue<T> {
        private List<T> queue;
        private int capacity;
        private int current = 0;

        public CustomQueue(int capacity) {
            this.capacity = capacity;
            queue = new ArrayList<>(capacity);
        }

        public void put(T value) throws InterruptedException {
            synchronized (this) {
                while (current >= capacity) {
                    wait();
                }
                queue.add(value);
                current++;
                notifyAll();
            }
        }

        public T take() throws InterruptedException {
            synchronized (this) {
                    while (current <= 0) {
                        wait();
                    }
                    T value = queue.get(current-1);
                    current--;
                    notifyAll();
                    return value;
            }
        }
    }

    static CustomQueue<String> queue = new CustomQueue<>(5);

    public static void main(String[] args) throws InterruptedException {

        Runnable producer = () -> {
            while(true) {
                try {
                    queue.put("Added message from Producer " + Thread.currentThread().getName());
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Runnable consumer = () -> {
            while(true) {
                try {
                    System.out.println(queue.take());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };

        Thread t1 = new Thread(producer);
        Thread t2 = new Thread(consumer);
        Thread t3 = new Thread(consumer);
        Thread t4 = new Thread(consumer);
        Thread t5 = new Thread(consumer);

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        System.out.println("That's it, now just wait");
    }
}

/*ALTERNATIVE with ReentrantLock and Condition
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class LockBasedBlockingQueue<T> {
    private final Queue<T> queue;
    private final int capacity;
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public LockBasedBlockingQueue(int capacity) {
        this.queue = new LinkedList<>();
        this.capacity = capacity;
    }

    public void enqueue(T item) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                notFull.await(); // Wait if queue is full
            }
            queue.add(item);
            notEmpty.signal(); // Notify consumers
        } finally {
            lock.unlock();
        }
    }

    public T dequeue() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                notEmpty.await(); // Wait if queue is empty
            }
            T item = queue.poll();
            notFull.signal(); // Notify producers
            return item;
        } finally {
            lock.unlock();
        }
    }
}

*/
