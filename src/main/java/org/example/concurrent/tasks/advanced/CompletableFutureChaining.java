package org.example.concurrent.tasks.advanced;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/*  CompletableFuture Chaining
    Task: Chain multiple async computations (e.g., fetch user → fetch orders → calculate total).
    Goal: Master the fluent async API of CompletableFuture.
*/
public class CompletableFutureChaining {
    record User(int id, String name){}
    record Order(int id, int user_id, double totalPrice){}

    static ExecutorService executor = Executors.newFixedThreadPool(4);

    public static void main(String[] args) throws Exception {
        int userId = 1;

        CompletableFuture<Double> totalFuture = fetchUser(userId)
                .thenCompose(CompletableFutureChaining::fetchOrders)
                .thenCompose(CompletableFutureChaining::calculateTotal);

        // Final result
        totalFuture.thenAccept(total ->
                System.out.println("Total order value: $" + total)
        ).join();

        executor.shutdown();
    }

    static CompletableFuture<User> fetchUser(int id) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(500);
            System.out.println("Fetched user: " + id);
            return new User(id, "AnyUser");
        }, executor);
    }

    static CompletableFuture<List<Order>> fetchOrders(User user) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            System.out.println("Fetched orders for user: " + user.name);
            return Stream.of(
                    new Order(101, 1, 99.99),
                    new Order(102, 1, 149.50),
                    new Order(103, 2, 59.75)
            ).filter(o -> o.user_id() == user.id()).toList();
        }, executor);
    }

    static CompletableFuture<Double> calculateTotal(List<Order> orders) {
        return CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            double total = orders.stream().mapToDouble(Order::totalPrice).sum();
            System.out.println("Calculated total: $" + total);
            return total;
        }, executor);
    }

    static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
