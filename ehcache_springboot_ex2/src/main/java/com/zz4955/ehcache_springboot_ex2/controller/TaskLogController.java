package com.zz4955.ehcache_springboot_ex2.controller;

import com.zz4955.ehcache_springboot_ex2.bean.Tasklog;
import com.zz4955.ehcache_springboot_ex2.service.TaskLogService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 测试控制层
 */
@RestController
@RequestMapping("/taskLog")
public class TaskLogController {

    private static AtomicInteger num = new AtomicInteger(0);

    @Autowired
    private TaskLogService taskLogService;

    /**
     * 根据ID查询
     * @return
     */
    @RequestMapping("/get")
    public Tasklog getById(String id) {
        return taskLogService.findById(id);
    }

    /**
     * 添加数据
     * @return
     */
    @RequestMapping("/add")
    public String add() {
        Tasklog bean = new Tasklog();
        bean.setId(Integer.valueOf(num.incrementAndGet()).toString());
        bean.setTaskId(bean.getId());
        taskLogService.create(bean);
        return "插入成功";
    }

    /**
     * 更新数据
     * @return
     */
    @RequestMapping("/update")
    public String update(String id) {
        Tasklog bean = new Tasklog();
        bean.setId(id);
        bean.setTaskId(id);

        bean = taskLogService.update(bean);
        if (bean != null) {
            return "更新成功";
        } else {
            return "更新失败";
        }
    }

    /**
     * 删除数据
     * @return
     */
    @RequestMapping("/remove")
    public String romove(String id) {
        taskLogService.delete(id);
        return "删除成功";
    }

    /**
     * 控制台输出缓存
     * @param id
     */
    @RequestMapping("/getCache")
    public void getCache(String id) {
        taskLogService.getCache();
    }
}
