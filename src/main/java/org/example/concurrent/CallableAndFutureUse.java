package org.example.concurrent;

import java.util.concurrent.*;

public class CallableAndFutureUse {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Future<Integer> task1 = executor.submit(new Task(1));
        Future<Integer> task2 = executor.submit(new Task(2));

        try {
            System.out.println("Result from task 1: " + task1.get());
            System.out.println("Result from task 2: " + task2.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    static class Task implements Callable<Integer> {
        private final int taskId;
        public Task(int taskId ) {
            this.taskId = taskId;
        }
        @Override
        public Integer call() throws Exception {
            System.out.println("Executing task " + taskId + " by " + Thread.currentThread().getName());
            Thread.sleep(1000);
            return taskId * 10;
        }
    }
}
