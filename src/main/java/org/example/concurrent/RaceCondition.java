package org.example.concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

//Safe counter
class Counter implements Runnable
{
    private final AtomicInteger  c = new AtomicInteger(0);
    public void increment() {
        c.incrementAndGet();
    }

    public int getValue() {
        return c.get();
    }

    @Override
    public void run() {
            this.increment();
    }
}

//Unsafe counter
/*class Counter implements Runnable
{
    private int  c = 0;
    public void increment() {
        c++;
    }

    public int getValue() {
        return c;
    }

    @Override
    public void run() {
        this.increment();
    }
}*/
public class RaceCondition {
    private static int count = 0;
    private static final ReentrantLock lock = new ReentrantLock();
    public static void main(String args[]) throws InterruptedException {
        /*Counter counter = new Counter();
        List<Thread> threads = new ArrayList<>();
        for(int i = 0; i < 10000; i++) {
            threads.add(new Thread(counter));

        }
        for(int i = 0; i < 10000; i++) {
            threads.get(i).start();
        }
        for(int i = 0; i < 10000; i++) {
            threads.get(i).join();
        }
        System.out.println(counter.getValue());*/

        Runnable runnable1 = () -> {
            for(int i = 0; i < 100000; i++) {
                lock.lock();
                count++;
                lock.unlock();
            }
            System.out.println(Thread.currentThread().getName() + " " + count);
        };

        Runnable runnable2 = () -> {
            for(int i = 0; i < 100000; i++) {
                lock.lock();
                    count++;
                    lock.unlock();
            }
            System.out.println(Thread.currentThread().getName() + " " + count);
        };

        Thread thread1 = new Thread(runnable1);
        Thread thread2 = new Thread(runnable2);
        thread2.start();
        thread1.start();
        //thread1.join();
        //thread2.join();
    }
}
