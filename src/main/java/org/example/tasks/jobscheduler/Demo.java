package org.example.tasks.jobscheduler;

import java.time.LocalDateTime;

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        Scheduler scheduler = new Scheduler();

        scheduler.schedule(() -> System.out.println("Task 1 executed at " + LocalDateTime.now()), 2000);

        scheduler.scheduleAtFixedRate(() -> System.out.println("Repeating task at " + LocalDateTime.now()), 1000, 3000);

        TaskHandle handle = scheduler.schedule(() -> System.out.println("Cancelled task should not run "), 2000);
        scheduler.cancel(handle);

        Thread.sleep(10000);
        scheduler.shutdown();
    }
}
