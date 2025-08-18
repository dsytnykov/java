package org.example.tasks;

import java.io.Serial;
import java.io.Serializable;

/*Task:
Create a thread-safe Singleton class using lazy initialization and double-checked locking.

Requirements:
The class should have only one instance.
It should be lazy-initialized (created only when needed).
It should be thread-safe without excessive synchronization overhead.
Bonus:
Prevent reflection-based breaking of the singleton pattern.
Prevent serialization/deserialization issues.*/
public class Singleton implements Serializable {
    private static volatile Singleton instance;

    private Singleton(){
        if(instance != null) {
            throw new RuntimeException("Instance can't be created");
        }
    };

    public static Singleton getInstance() {
        if(instance == null) {
            synchronized (Singleton.class) {
                if(instance == null) {
                    return new Singleton();
                }
            }
        }
        return instance;
    }

    @Serial
    protected Object readResolve() {
        return getInstance();
    }
}
