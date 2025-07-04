package org.example.tasks.oddeventhreads;

public class OddEvenPrintThread {
    private final static int N = 10;
    private int count = 1;

    public void oddThreadTask() {
        while(count < N) {
            synchronized (this) {
                while(count % 2 == 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + ": " + count++);
                notify();
            }
        }
    }

    public void evenThreadTask() {
        while(count < N) {
            synchronized (this) {
                while (count % 2 == 1) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + ": " + count++);
                notify();
            }
        }
    }

    public static void main(String[] args) {
        OddEvenPrintThread oddEvenPrintThread = new OddEvenPrintThread();
        Thread oddThread = new Thread(oddEvenPrintThread::oddThreadTask);
        Thread evenThread = new Thread(oddEvenPrintThread::evenThreadTask);

        oddThread.start();
        evenThread.start();
    }
}
