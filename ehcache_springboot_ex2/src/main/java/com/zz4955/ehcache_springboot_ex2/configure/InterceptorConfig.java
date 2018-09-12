package com.zz4955.ehcache_springboot_ex2.configure;

import com.zz4955.ehcache_springboot_ex2.interceptor.LogIdInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    @Bean
    public HandlerInterceptor getLogIdInterceptor() {
        return new LogIdInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getLogIdInterceptor()).addPathPatterns("/**");
    }
}
