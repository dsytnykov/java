package org.example.tasks.eventlistener;

public class EventSystemTest {
    public static void main(String[] args) throws InterruptedException {
        EventBus eventBus = new EventBus();

        //create and register listeners
        EventListener el1 = event -> System.out.println("Event listener 1 received " + event.type() + "->" + event.data());
        EventListener el2 = event -> System.out.println("Event listener 2 received " + event.type() + "->" + event.data());

        eventBus.register("USER_REGISTERED", el1);
        eventBus.register("USER_REGISTERED", el2);

        //publish event
        eventBus.publish(new Event("USER_REGISTERED", "John Doe"));
        eventBus.publish(new Event("USER_REGISTERED", "Jane Smith"));

        Thread.sleep(1000);
        eventBus.shutdown();
    }

}