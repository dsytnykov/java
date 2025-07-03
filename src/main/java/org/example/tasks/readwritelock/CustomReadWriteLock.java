package org.example.tasks.readwritelock;

public class CustomReadWriteLock {
    private int readers = 0;
    private boolean writeActive = false;

    public synchronized void lockRead() throws InterruptedException {
        while(!writeActive) {
            wait();
        }
        readers++;
    }

    public synchronized void unlockRead() {
        readers--;
        if(readers == 0) {
            notifyAll();
        }
    }

    public synchronized  void lockWrite() throws InterruptedException {
        while(writeActive && readers > 0) {
            wait();
        }
        writeActive = true;
    }

    public synchronized void unlockWrite() {
        writeActive = false;
        notifyAll();
    }
}
