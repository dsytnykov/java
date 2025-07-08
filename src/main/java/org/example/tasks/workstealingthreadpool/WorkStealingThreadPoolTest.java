package org.example.tasks.workstealingthreadpool;

/*4️⃣ Implement a Thread-Pool with Work-Stealing Algorithm
🔹 Create a custom thread-pool where idle threads steal tasks from busy threads.
🔹 Use deque-based task queues to reduce contention.
🔹 Optimize load balancing and CPU efficiency using ForkJoinPool concepts.*/
public class WorkStealingThreadPoolTest {
    public static void main(String[] args) {
        WorkStealingThreadPool pool = new WorkStealingThreadPool(4);

        // Submit tasks
        for (int i = 1; i <= 10; i++) {
            final int taskNumber = i;
            pool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " executing task " + taskNumber);
                try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            });
        }

        // Allow tasks to complete, then shutdown
        try { Thread.sleep(5000); } catch (InterruptedException ignored) {}
        pool.shutdown();
    }
}
