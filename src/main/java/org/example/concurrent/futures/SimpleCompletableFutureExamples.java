package org.example.concurrent.futures;

import java.util.concurrent.CompletableFuture;

public class SimpleCompletableFutureExamples {
    public static void main(String[] args) {
        // simple CompletableFuture
        CompletableFuture<Void> futureRunAsync = CompletableFuture.runAsync(() -> {
            System.out.println("I am inside asynchronous task");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        futureRunAsync.join();
        System.out.println("Main thread");

        // CompletableFuture with result
        CompletableFuture<Integer> futureSupplyAsync = CompletableFuture.supplyAsync(() -> 5 * 6);
        futureSupplyAsync.thenAccept(System.out::println).join();

        // Combining CompletableFutures
        CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> 5);
        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 25);
        CompletableFuture<Integer> futureResult = future1.thenCombine(future2, Integer::sum);
        futureResult.thenAccept(System.out::println).join();

        // Multiple futures with allOf
        CompletableFuture<Void> futureAll1 = CompletableFuture.runAsync(() -> {
            System.out.println("I am inside asynchronous task 1");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        CompletableFuture<Void> futureAll2 = CompletableFuture.runAsync(() -> {
            System.out.println("I am inside asynchronous task 2");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        CompletableFuture<Void> futureAllOf = CompletableFuture.allOf(future1, future2);
        futureAllOf.join();
        System.out.println("All tasks executed");

        //Error handling with CompletableFuture
        CompletableFuture<Object> futureException = CompletableFuture.supplyAsync(() -> {
            throw new RuntimeException("Exception occurred in asynchronous task");
        }).exceptionally(e -> {
            System.out.println("Exception handled: " + e.getMessage());
            return null;
        });
        futureException.join();

        //Chain multiple asynchronous operations
        CompletableFuture<Integer> futureChain = CompletableFuture.supplyAsync(() -> 5)
                .thenApplyAsync(res -> res * 5)
                .thenApplyAsync(res -> res * 2);
        futureChain.thenAccept(System.out::println).join();
    }
}
