package org.example.tasks.eventlistener;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventBus {
    private final Map<String, List<EventListener>> listeners = new ConcurrentHashMap<>();
    private final ExecutorService executor = Executors.newCachedThreadPool();

    public void register(String type, EventListener listener) {
        listeners.computeIfAbsent(type, k -> new CopyOnWriteArrayList<>()).add(listener);
    }

    public void unregister(String type, EventListener listener) {
        List<EventListener> eventListeners = listeners.get(type);
        if(eventListeners != null) {
            eventListeners.remove(listener);
            if(eventListeners.isEmpty()) {
                listeners.remove(type);
            }
        }
    }

    public void publish(Event event) {
        List<EventListener> eventListeners = listeners.get(event.type());
        if(eventListeners != null) {
            for(EventListener listener : eventListeners) {
                executor.submit(() -> listener.handleEvent(event));
            }
        }
    }

    public void shutdown() {
        executor.shutdown();
    }
}
