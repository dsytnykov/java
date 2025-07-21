package org.example.concurrent.tasks.intermediate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*  Future and Callable
    Task: Submit Callable tasks that return results. Collect and combine them.
    Goal: Practice asynchronous programming with Future.*/
public class AsynchronousTask {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 2);

        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30);

        long startTime = System.currentTimeMillis();

        List<CompletableFuture<Integer>> futures = numbers.stream()
                .map(n ->
                        CompletableFuture.supplyAsync(() -> {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            return n * n;
                        }, executor))
                .toList();

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        CompletableFuture<List<Integer>> allOfResult = allOf.thenApply(v -> futures.stream()
                .map(CompletableFuture::join)
                .toList());

        int sum = allOfResult.get().stream().mapToInt(Integer::intValue).sum();
        System.out.println("Sum of squares: " + sum + " in " + (System.currentTimeMillis() - startTime) + " ms");

        executor.shutdown();
    }
}
