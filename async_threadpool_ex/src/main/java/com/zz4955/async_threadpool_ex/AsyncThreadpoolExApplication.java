package com.zz4955.async_threadpool_ex;

import com.zz4955.async_threadpool_ex.configuration.TaskThreadPoolConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties({TaskThreadPoolConfig.class}) // 开启配置属性支持
public class AsyncThreadpoolExApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsyncThreadpoolExApplication.class, args);
	}
}
