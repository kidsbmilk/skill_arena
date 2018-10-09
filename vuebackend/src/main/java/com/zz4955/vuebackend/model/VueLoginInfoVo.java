package com.zz4955.vuebackend.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class VueLoginInfoVo {

    @NotNull(message = "用户名不允许为空")
    private String username;

    @NotNull(message = "密码不能为空")
    private String password;
}
