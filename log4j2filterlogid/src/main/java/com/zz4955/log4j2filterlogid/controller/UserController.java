package com.zz4955.log4j2filterlogid.controller;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@RestController
@SpringBootApplication
@RequestMapping(value = "/springboot")
public class UserController {

    @RequestMapping(value = "/getUserByGet", method = RequestMethod.GET)
    public String getUserByGet(@RequestParam(value = "userName") String userName) {
        return "Hello " + userName;
    }

    @RequestMapping(value = "/getUserByPost", method = RequestMethod.POST)
    public String getUserByPost(@RequestParam(value = "userName") String userName) {
        return "Hello " + userName;
    }

    @RequestMapping(value = "/getUserByJson", method = RequestMethod.POST)
    public String getUserByJson(@RequestBody String data) {
        return "Json is " + data;
    }
}
