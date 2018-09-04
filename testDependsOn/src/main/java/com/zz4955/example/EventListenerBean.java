package com.zz4955.example;

public class EventListenerBean {

    public void initialize() {
        System.out.println("EventListenerBean initializing");
        EventManager.getInstance().addListener(s ->
                System.out.println("event received in EventListenerBean: " + s));
    }
}
