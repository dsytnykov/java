package org.example.tasks.readwritelock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*Implement a Concurrent Read-Write Lock for a Shared Resource
Develop a custom read-write lock without using ReentrantReadWriteLock.

Multiple threads can read simultaneously.
Only one thread can write at a time (exclusive lock).
Readers must wait if a writer is active.
Writers must wait if there are active readers.*/
public class CustomReadWriteLockTest {
    public static void main(String[] args) {
        CustomReadWriteLock lock = new CustomReadWriteLock();
        ExecutorService executor = Executors.newFixedThreadPool(5);

        Runnable readerTask = () -> {
            try {
                lock.lockRead();
                System.out.println(Thread.currentThread().getName() + " is reading...");
                Thread.sleep(1000);
                lock.unlockRead();
                System.out.println(Thread.currentThread().getName() + " finished reading.");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        };

        Runnable writeTask = () -> {
            try {
                lock.lockWrite();
                System.out.println(Thread.currentThread().getName() + " is writing...");
                Thread.sleep(2000);
                lock.unlockWrite();
                System.out.println(Thread.currentThread().getName() + " finished writing.");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        executor.execute(readerTask);
        executor.execute(readerTask);
        executor.execute(writeTask);
        executor.execute(readerTask);
        executor.execute(writeTask);

        executor.shutdown();
    }
}
