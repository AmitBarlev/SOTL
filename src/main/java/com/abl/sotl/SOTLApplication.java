package com.abl.sotl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
public class SOTLApplication {

	public static void main(String[] args) {
		SpringApplication.run(SOTLApplication.class, args);
	}

}
