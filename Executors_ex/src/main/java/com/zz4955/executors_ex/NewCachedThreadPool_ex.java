package com.zz4955.executors_ex;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NewCachedThreadPool_ex {

    public static void main(String[] args) {
        final CountDownLatch finish = new CountDownLatch(10);
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for(int i = 0; i < 10; i++) {
            final int index = i;

            cachedThreadPool.execute(new Runnable() {
                public void run() {
                    System.out.println(index);
                    System.out.println(Thread.currentThread()); // 可以看到，有线程复用的情况存在。
                    finish.countDown();
                }
            });
        }
        try {
            finish.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cachedThreadPool.shutdownNow();
    }
}
