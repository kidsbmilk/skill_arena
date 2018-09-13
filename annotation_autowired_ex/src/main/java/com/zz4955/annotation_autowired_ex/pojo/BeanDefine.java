package com.zz4955.annotation_autowired_ex.pojo;

public class BeanDefine {

    private String id;
    private String className;

    public BeanDefine(String id, String className) {
        this.id = id;
        this.className = className;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
