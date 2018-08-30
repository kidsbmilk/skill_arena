package com.zz4955.async_threadpool_ex.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // 这个写成Controller会出错
@RequestMapping("/test")
public class TestController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/testlog")
    public String testlog() {
        LOGGER.info("testlog");
        return "OK";
    }
}
