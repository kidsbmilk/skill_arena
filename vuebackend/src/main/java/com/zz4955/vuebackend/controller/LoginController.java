package com.zz4955.vuebackend.controller;

import com.zz4955.vuebackend.model.Result;
import com.zz4955.vuebackend.model.ResultFactory;
import com.zz4955.vuebackend.model.VueLoginInfoVo;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@Controller
public class LoginController {

    @CrossOrigin
    @RequestMapping(value = "/api/login", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Result login(@Valid @RequestBody VueLoginInfoVo loginInfoVo, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            String message = String.format("登录失败，详细信息[%s]。", bindingResult.getFieldError().getDefaultMessage());
            return ResultFactory.buildFailResult(message);
        }
        if(!Objects.equals("vue3", loginInfoVo.getUsername()) || !Objects.equals("123456", loginInfoVo.getPassword())) {
            String message = String.format("登录失败，详细信息[用户名或者密码不正确].");
            return ResultFactory.buildFailResult(message);
        }
        return ResultFactory.buildSuccessResult("登录成功。");
    }
}
