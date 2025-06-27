package org.example.tasks.nonblockingboundedqueue;

public class CustomNonBlockedQueueTest {
    public static void main(String[] args) {
        CustomNonBlockedQueue<Integer> queue = new CustomNonBlockedQueue<>(5);

        // Producer thread
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                if (queue.enqueue(i)) {
                    System.out.println("Enqueued: " + i);
                } else {
                    System.out.println("Queue is full!");
                }
            }
        });

        // Consumer thread
        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                Integer item;
                while ((item = queue.dequeue()) == null) {
                    Thread.yield(); // Yield CPU if queue is empty
                }
                System.out.println("Dequeued: " + item);
            }
        });

        producer.start();
        consumer.start();
    }
}
