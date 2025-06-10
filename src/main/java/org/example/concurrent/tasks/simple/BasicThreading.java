package org.example.concurrent.tasks.simple;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*🟢 Level 1–3: Basic Threading*/
public class BasicThreading {

    /*1. Hello Thread
    Task: Create and run a simple thread that prints "Hello from a thread!".
    Goal: Understand how to extend Thread or implement Runnable.*/
    /*public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> System.out.println("Hello from a thread!"));
        t.start();
        t.join();
    }*/

    /*2. Multi-threaded Counter
    Task: Start multiple threads incrementing a shared counter.
    Challenge: Without synchronization, observe race conditions.
    Goal: Learn about thread safety issues.*/
    /*3. Synchronized Counter Fix
    Task: Fix the counter from Task #2 using synchronized or ReentrantLock.
    Goal: Practice synchronization primitives.*/
    static int counter = 0;
    private static int MAX = 100_000;

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        Runnable r = () -> {
                for (int i = 0; i < MAX; i++) {
                    try {
                        lock.lock();
                        counter++;
                    } finally {
                        lock.unlock();
                    }
                }
        };
        Thread t1 = new Thread(r);
        Thread t2 = new Thread(r);

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(counter);
    }

}

