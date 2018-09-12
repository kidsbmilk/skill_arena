package com.zz4955.ehcache_springboot_ex.bean;

import java.io.Serializable;

public class Tasklog implements Serializable{

    public String id;
    public String taskId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return "Tasklog{" +
                "id='" + id + '\'' +
                ", taskId='" + taskId + '\'' +
                '}';
    }
}
