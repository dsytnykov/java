package org.example.tasks.customthreadpool;

/* Implement a Fixed-Size Thread Pool (Executor Service)
Task:
Build a custom thread pool that:

Accepts tasks (Runnable).
Uses a fixed number of worker threads.
Queues tasks if all threads are busy.
Shuts down gracefully when requested.
Requirements:
Manage worker threads efficiently (avoid creating/destroying threads frequently).
Handle graceful shutdown (finish running tasks before exiting).
Implement a task queue using BlockingQueue.
*/
public class FixedSizeThreadPool {

    public static void main(String[] args) throws InterruptedException {
        CustomThreadPool pool = new CustomThreadPool(3); // 3 worker threads

        for (int i = 1; i <= 10; i++) {
            final int taskNumber = i;
            pool.submit(() -> {
                System.out.println("Task " + taskNumber + " is running on thread " + Thread.currentThread().getName());
                try {
                    Thread.sleep(2000); // Simulate task processing time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        Thread.sleep(5000); // Allow some tasks to execute
        pool.shutdown(); // Shutdown thread pool
    }
}
