package com.zz4955.async_ex.controller;

import com.zz4955.async_ex.task.NormalTask;
import com.zz4955.async_ex.task.AsyncTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * spring boot中使用@Async实现异步调用任务
 * https://www.jb51.net/article/106718.htm
 */
@RestController
@RequestMapping("/async_ex/")
public class RunTask {

    private final NormalTask normalTask;
    private final AsyncTask asyncTask;

    @Autowired
    public RunTask(NormalTask normalTask, AsyncTask asyncTask) {
        this.normalTask = normalTask;
        this.asyncTask = asyncTask;
    }

    @RequestMapping("/normal_task")
    public String task1() throws Exception {
        normalTask.doTaskOne();;
        normalTask.doTaskTwo();
        normalTask.doTaskThree();
        return "normal task";
    }

    @RequestMapping("/async_task")
    public String task2() throws Exception {
        asyncTask.doTaskOne();
        asyncTask.doTaskTwo();
        asyncTask.doTaskThree();
        return "async task";
    }
}
