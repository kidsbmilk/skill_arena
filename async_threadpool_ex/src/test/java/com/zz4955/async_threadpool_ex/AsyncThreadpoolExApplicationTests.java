package com.zz4955.async_threadpool_ex;

import com.zz4955.async_threadpool_ex.pool.AsyncTask;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutionException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AsyncThreadpoolExApplicationTests {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private AsyncTask asyncTask;

	@Test
	public void AsyncTaskTest() throws InterruptedException, ExecutionException {

	    for(int i = 0; i < 100; i ++) {
	        asyncTask.doTask1(i);
        }
        Thread.sleep(3 * 1000);
        logger.info("All tasks finished.");
	}

    @Test
    public void AsyncTaskTest2() throws InterruptedException, ExecutionException {

        for(int i = 0; i < 100; i ++) {
            asyncTask.doTask2(i);
        }
        Thread.sleep(3 * 1000);
        logger.info("All tasks finished.");
    }
}
