package com.zz4955.vuebackend.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {

    private int code;
    private String message;
    private Object data;

    Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
