package com.zz4955.ehcache_springboot_ex2.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Tasklog implements Serializable{

    public String id;
    public String taskId;
}
