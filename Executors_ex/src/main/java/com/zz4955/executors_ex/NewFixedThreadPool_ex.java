package com.zz4955.executors_ex;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewFixedThreadPool_ex {

    public static void main(String[] args) {
        final CountDownLatch finish = new CountDownLatch(10);
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(2);
        for(int i = 0; i < 10; i ++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                public void run() {
                    System.out.println(index);
                    System.out.println(Thread.currentThread());
                    finish.countDown();
                }
            });
        }
        try {
            finish.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        fixedThreadPool.shutdownNow();
    }
}