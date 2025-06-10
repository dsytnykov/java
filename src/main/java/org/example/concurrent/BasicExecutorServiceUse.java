package org.example.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BasicExecutorServiceUse {
    public static void main(String[] args) {
        try (ExecutorService executor = Executors.newFixedThreadPool(5)) {
            for (int i = 0; i < 5; i++) {
                final int taskId = i;
                executor.submit(() -> {
                    System.out.println("Executing task " + taskId + " by " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });
            }
            executor.shutdown();
        }
    }
}
