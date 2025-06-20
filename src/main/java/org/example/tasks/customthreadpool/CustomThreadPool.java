package org.example.tasks.customthreadpool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CustomThreadPool {
    private final WorkerThread[] workers;
    private final BlockingQueue<Runnable> queue;
    private volatile boolean isShutdown = false;

    public CustomThreadPool(int poolSize) {
        this.queue = new LinkedBlockingQueue<>();
        this.workers = new WorkerThread[poolSize];

        for(int i = 0; i < poolSize; i++) {
            workers[i] = new WorkerThread();
            workers[i].start();
        }
    }

    public void submit(Runnable task) throws InterruptedException {
        if(!isShutdown) {
            queue.put(task);
        } else {
            throw new IllegalStateException("ThreadPool is shutting down, cannot accept new tasks");
        }
    }

    public void shutdown() {
        isShutdown = true;
        for(WorkerThread w : workers) {
            w.interrupt();
        }
    }

    private class WorkerThread extends Thread {
        public void run() {
            while(true) {
                try {
                    Runnable task = queue.take();
                    task.run();
                } catch(InterruptedException e) {
                    break;
                }
            }
        }
    }
}
