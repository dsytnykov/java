package org.example.concurrent.tasks.advanced;

import java.util.concurrent.atomic.AtomicInteger;

/*
    Massive Concurrency with Virtual Threads
    Task: Launch 100,000 virtual threads to simulate I/O-bound work (e.g., Thread.sleep).
    Goal: Explore the scalability of virtual threads with Thread.ofVirtual().start(...).
*/
public class VirtualThreadsExploring {
    public static final int THREAD_COUNT = 100_000;
    private final static AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) {

        long start = System.nanoTime();

        for(int i = 0; i < THREAD_COUNT; i++) {
            Thread.ofVirtual().start(() -> {
                counter.incrementAndGet();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        long end = System.nanoTime();

        try {
            Thread.sleep(2000);//just an option to wait until all threads are finished
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.printf("All threads launched in %.2f ms and reached %d%n", (end - start) / 1_000_000.0, counter.get());

    }
}
