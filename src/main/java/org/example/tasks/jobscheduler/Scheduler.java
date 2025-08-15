package org.example.tasks.jobscheduler;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/*
Implement a Multi-Threaded Job Scheduler
Objective: Design a scheduler that can run tasks at fixed intervals or after delays.
Support scheduling, cancellation, and priority tasks.
*/
public class Scheduler {
    private final PriorityBlockingQueue<ScheduledTask> taskQueue = new PriorityBlockingQueue<>();
    private final ConcurrentHashMap<UUID, ScheduledTask> taskMap = new ConcurrentHashMap<>();
    private final AtomicBoolean running = new AtomicBoolean(true);
    private final Thread workerThread;

    public Scheduler() {
        workerThread = new Thread(new Worker());
        workerThread.start();
    }

    public TaskHandle schedule(Runnable task, long delayMillis) {
        return scheduleAtFixedRate(task, delayMillis, 0);
    }

    public TaskHandle scheduleAtFixedRate(Runnable task, long initialDelay, long intervalMillis) {
        ScheduledTask scheduledTask = new ScheduledTask(task, initialDelay, intervalMillis);

        taskQueue.put(scheduledTask);
        taskMap.put(scheduledTask.getId(), scheduledTask);
        return new TaskHandle(scheduledTask.getId());
    }

    public boolean cancel(TaskHandle handle) {
        ScheduledTask task = taskMap.remove(handle.id());
        if (task != null) {
            task.cancel();
            return taskQueue.remove(task);
        }
        return false;
    }

    public void shutdown() {
        running.set(false);
        workerThread.interrupt();
    }

    private class Worker implements Runnable {
        @Override
        public void run() {
            while (running.get()) {
                try {
                    ScheduledTask nextTask = taskQueue.peek();

                    if (nextTask == null) {
                        TimeUnit.MILLISECONDS.sleep(50);
                        continue;
                    }

                    long now = System.currentTimeMillis();
                    long delay = nextTask.getNextExecutionTime() - now;

                    if (delay > 0) {
                        TimeUnit.MILLISECONDS.sleep(Math.min(delay, 50));
                        continue;
                    }

                    nextTask = taskQueue.poll();

                    if (nextTask != null && !nextTask.isCancelled()) {
                        new Thread(nextTask.getTask()).start();

                        if (nextTask.isRecurring()) {
                            nextTask.updateNextExecutionTime();
                            taskQueue.put(nextTask);
                        } else {
                            taskMap.remove(nextTask.getId());
                        }
                    }

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

}
