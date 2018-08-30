package com.zz4955.log4j2filterlogid.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class MyAction {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/")
    public String home() {
        return "Hello World!";
    }

    @RequestMapping("/next")
    public String next(HttpServletRequest request, HttpServletResponse response) {
        String reqURL = request.getRequestURI();
        logger.info("reqURL: " + reqURL);
        return "next!";
    }

    @RequestMapping("/reset")
    @ResponseBody
    public Object reset(HttpServletRequest request, HttpServletResponse response) {
        String reqURL = request.getRequestURI();
        String queryString = request.getQueryString();
        String orgURI = (String) request.getAttribute("requestURI");
        logger.debug("reqURL: " + reqURL);
        logger.debug("queryString: " + queryString);
        String targetIP = "http://localhost:8888/springboot/getUserByGet";
        String targetURL = targetIP.concat("?").concat(queryString + "reset");
        logger.debug("targetURL: " + targetURL);

//        Enumeration<String> params = request.getParameterNames();
//        Map<String, String> paramMap = new HashMap<String, String>();
//        while(params.hasMoreElements()) {
//            String paraName = params.nextElement();
//            paramMap.put(paraName, request.getParameter(paraName));
//        }
//        String json = restTemplate.postForEntity(targetURL, paramMap, String.class).getBody();
        String json = restTemplate.getForEntity(targetURL, String.class).getBody();
        System.out.println(json);
        return json;
    }
}
