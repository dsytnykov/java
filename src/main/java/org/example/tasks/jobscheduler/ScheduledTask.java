package org.example.tasks.jobscheduler;

import java.util.UUID;

class ScheduledTask implements Comparable<ScheduledTask> {
    private final Runnable task;
    private final long interval;
    private long nextExecutionTime;
    private final UUID id = UUID.randomUUID();
    private boolean cancelled = false;

    public ScheduledTask(Runnable task, long delay, long interval) {
        this.task = task;
        this.interval = interval;
        this.nextExecutionTime = System.currentTimeMillis() + delay;
    }

    public Runnable getTask() {
        return task;
    }

    public long getNextExecutionTime() {
        return nextExecutionTime;
    }

    public boolean isRecurring() {
        return interval > 0;
    }

    public void updateNextExecutionTime() {
        nextExecutionTime = System.currentTimeMillis() + interval;
    }

    public UUID getId() {
        return id;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void cancel() {
        cancelled = true;
    }

    @Override
    public int compareTo(ScheduledTask other) {
        return Long.compare(this.nextExecutionTime, other.getNextExecutionTime());
    }
}
