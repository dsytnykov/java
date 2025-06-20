package org.example.tasks.eventlistener;

@FunctionalInterface
public interface EventListener {
    void handleEvent(Event event);
}
