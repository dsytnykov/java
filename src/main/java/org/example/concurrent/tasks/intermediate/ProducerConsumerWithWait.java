package org.example.concurrent.tasks.intermediate;

import java.util.LinkedList;
import java.util.List;

/*Producer-Consumer Using Wait/Notify
    Task: Implement a producer-consumer pattern with a shared buffer.
    Constraints: Use wait() and notify(), avoid concurrent collections.
    Goal: Understand low-level coordination.*/
public class ProducerConsumerWithWait {

    private final List<String> queue = new LinkedList<>();
    private final int capacity;
    private final Object lock = new Object();

    public ProducerConsumerWithWait(int capacity) {
        this.capacity = capacity;
    }

    public void put(String value) throws InterruptedException {
        synchronized (lock) {
            if(queue.size() >= capacity) {
                wait();
            }
            queue.add(value);
            notifyAll();
        }
    }

    public String take() throws InterruptedException {
        synchronized (lock) {
            if(queue.isEmpty()) {
                wait();
            }
            String value = queue.removeFirst();
            notifyAll();
            return value;
        }
    }
}
