package com.zz4955.example;

public class EventPublisherBean {

    public void initialize() {
        System.out.println("EventPublisherBean initializing");
        EventManager.getInstance().publish("event published from EventPublisherBean");
    }
}
