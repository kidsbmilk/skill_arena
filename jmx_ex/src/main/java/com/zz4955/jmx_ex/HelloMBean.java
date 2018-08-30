package com.zz4955.jmx_ex;

public interface HelloMBean {

    public String getName();
    public void setName(String name);
    public void printHello();
    public void printHello(String whoName);
}
