package org.example.tasks.workstealingthreadpool;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public class WorkStealingThreadPool {
    private final int numThreads;
    private final List<Worker> workers;
    private final List<Deque<Runnable>> taskQueues;
    private final AtomicBoolean running = new AtomicBoolean(true);

    public WorkStealingThreadPool(int numThreads) {
        this.numThreads = numThreads;
        this.taskQueues = new ArrayList<>();
        this.workers = new ArrayList<>();

        for(int i = 0; i < numThreads; i++) {
            Deque<Runnable> taskQueue = new ConcurrentLinkedDeque<>();
            taskQueues.add(taskQueue);
            Worker worker = new Worker(taskQueue);
            workers.add(worker);
            new Thread(worker).start();
        }
    }

    public void submit(Runnable task) {
        int index = ThreadLocalRandom.current().nextInt(numThreads);
        taskQueues.get(index).offerLast(task);
    }

    public void shutdown() {
        running.set(false);
        for(Worker worker : workers) {
            worker.stop();
        }
    }

    private class Worker implements Runnable {
        private final Deque<Runnable> taskQueue;

        public Worker(Deque<Runnable> taskQueue) {
            this.taskQueue = taskQueue;
        }

        public void stop() {
            running.set(false);
        }

        @Override
        public void run() {
            while(running.get()) {
                Runnable task = taskQueue.pollLast();
                if(task == null) {
                    task = stealTask();
                }
                if(task != null) {
                    task.run();
                } else {
                    Thread.yield();
                }
            }
        }

        private Runnable stealTask() {
            for(int i = 0; i < numThreads; i++) {
                int victimIndex = ThreadLocalRandom.current().nextInt(numThreads);
                Deque<Runnable> victimQueue = taskQueues.get(victimIndex);
                Runnable stolenTask = victimQueue.pollFirst();
                if(stolenTask != null) {
                    return stolenTask;
                }
            }
            return null;
        }
    }
}
