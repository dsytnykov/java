package org.example.concurrent;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.List;
import java.util.stream.Collectors;
public class ApiAggregator {
    public static void main(String[] ar) {
        List<String> taskIds = Arrays.asList(
               "1", //new Task("Admin", RandomGenerator.getDefault().toString()).getId(),
               "2");// new Task("Comercial", RandomGenerator.getDefault().toString()).getId());

        List<CompletableFuture<String>> futureList = taskIds.stream()
                .map(taskId -> CompletableFuture.supplyAsync(() -> fetchResult(taskId)))
                .collect(Collectors.toList());

        // Create a combined Future using allOf()
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]));

        // When all the Futures are completed, call `future.join()` to get their results and collect the results in a list
        CompletableFuture<List<String>> allPageContentsFuture = allFutures.thenApply(v ->
                futureList.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList())
        );
// Now you can use `allPageContentsFuture` to get the combined results
        try {
            List<String> results = allPageContentsFuture.get(); // This gets the aggregated results
            // Process the combined results
            results.forEach(System.out::println);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
    // Dummy method to simulate fetching result
    private static String fetchResult(String taskId) {
        // Implement fetching logic
        return "Result for task " + taskId;
    }
}
