package org.example.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadStarvation {
    private static final Lock lock = new ReentrantLock(true); // Fair lock

    public static void main(String[] args) {
        Runnable task = () -> {
            while (true) {
                if (lock.tryLock()) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " acquired lock");
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                } /*else {
                    Thread.yield();
                }*/ //fixing the problem
            }
        };

        Thread highPriority = new Thread(task, "High-Priority");
        Thread lowPriority = new Thread(task, "Low-Priority");

        highPriority.setPriority(Thread.MAX_PRIORITY);
        lowPriority.setPriority(Thread.MIN_PRIORITY);

        highPriority.start();
        lowPriority.start();
    }
}
