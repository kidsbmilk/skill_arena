package com.zz4955.executors_ex;

import java.util.concurrent.*;

public class NewScheduledThreadPool_ex {

    public static void main(String[] args) {
        final CountDownLatch finish = new CountDownLatch(1);
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);
        ScheduledFuture future = scheduledThreadPool.schedule(new Runnable() {
            public void run() {
                System.out.println("delay 2 seconds");
                finish.countDown();
            }
        }, 2, TimeUnit.SECONDS);
        try {
            finish.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        scheduledThreadPool.shutdownNow();
        System.out.println("done.");
    }
}
