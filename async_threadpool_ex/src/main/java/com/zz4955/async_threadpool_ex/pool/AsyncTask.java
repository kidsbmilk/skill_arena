package com.zz4955.async_threadpool_ex.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncTask {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Async("myTaskAsyncPool") // myTaskAsyncPool配置线程池的方法名，此处如果不写自定义线程池的方法名，会使用默认的线程池
    public void doTask1(int i) throws InterruptedException {
        logger.warn(Thread.currentThread().getName());
        logger.warn("Task" + i + " started.");
    }

    @Async
    public void doTask2(int i) throws InterruptedException {
        logger.warn(Thread.currentThread().getName());
        logger.warn("Task" + i + " started.");
    }
}
