package org.example.concurrent;

import java.util.concurrent.CountDownLatch;

public class UtilityThreads {

    /*public static void createThreads() {
        Thread t1 = new Thread(() -> System.out.println("Thread 1"));
        Thread t2 = new Thread(() -> System.out.println("Thread 2"));
        Thread t3 = new Thread(() -> System.out.println("Thread 3"));

        try {
        t1.start();
        t1.join();
        t2.start();
        t2.join();
        t3.start();
        t3.join();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    }*/

    /*private static final Object lock = new Object();
    private static int turn = 1;
    public static void createThreads() {
        Thread t1 = new Thread(() -> {
            synchronized(lock) {
                while(turn != 1) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Thread 1 is running");
                turn = 2;
                lock.notifyAll();
            }
        });

        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                while (turn != 2) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Thread 2 is running");
                turn = 3;
                lock.notifyAll();
            }
        });
        Thread t3 = new Thread(() -> {
            synchronized(lock) {
                while(turn != 3) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Thread 3 is running");
                lock.notifyAll();
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }*/


    /*private static final Lock lock = new ReentrantLock();
    private static final Condition condition1 = lock.newCondition();
    private static final Condition condition2 = lock.newCondition();
    private static final Condition condition3 = lock.newCondition();
    private static int turn = 1;

    public static void createThreads() {
        Thread t1 = new Thread(() -> {
           lock.lock();
           try {
               while(turn != 1) {
                   condition1.await();
               }
               System.out.println("Thread 1 is running");
               turn = 2;
               condition2.signal();
           } catch (InterruptedException e) {
               e.printStackTrace();
           } finally {
               lock.unlock();
           }
        });

        Thread t2 = new Thread(() -> {
            lock.lock();
            try {
                while(turn != 2) {
                    condition2.await();
                }
                System.out.println("Thread 2 is running");
                turn = 3;
                condition3.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });
        Thread t3 = new Thread(() -> {
            lock.lock();
            try {
                while(turn != 3) {
                    condition3.await();
                }
                System.out.println("Thread 3 is running");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        });

        t1.start();
        t2.start();
        t3.start();
    }*/


    public static void createThreads() {
        CountDownLatch latch1 = new CountDownLatch(1);
        CountDownLatch latch2 = new CountDownLatch(1);

        Thread t1 = new Thread(() -> {
            System.out.println("Thread 1 is running");
            latch1.countDown();
        });

        Thread t2 = new Thread(() -> {
            try {
                latch1.await();
                System.out.println("Thread 2 is running");
                latch2.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                latch2.await();
                System.out.println("Thread 3 is running");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t1.start();
        t2.start();
        t3.start();

    }
}
