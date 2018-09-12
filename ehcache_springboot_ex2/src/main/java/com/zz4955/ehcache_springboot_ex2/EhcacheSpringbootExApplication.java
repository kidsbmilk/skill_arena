package com.zz4955.ehcache_springboot_ex2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zz4955.ehcache_springboot_ex2.mapper")
//@EnableCaching // 缓存启动，这个要配合ehcache-setting.xml一起使用，如果用EhCacheConfiguration bean来配置的话，是在EhCacheConfiguration这个bean里声明@EnableCaching注解。
public class EhcacheSpringbootExApplication {

	public static void main(String[] args) {
		SpringApplication.run(EhcacheSpringbootExApplication.class, args);
	}
}

// SpringBoot 集成Ehcache实现缓存
// https://my.oschina.net/sdlvzg/blog/1594834
