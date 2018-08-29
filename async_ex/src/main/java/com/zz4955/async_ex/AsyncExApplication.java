package com.zz4955.async_ex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AsyncExApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsyncExApplication.class, args);
	}
}
