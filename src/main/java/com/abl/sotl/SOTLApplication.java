package com.abl.sotl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.ReactiveRedisTemplate;

@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@EnableConfigurationProperties
public class SOTLApplication {

	public static void main(String[] args) {
		SpringApplication.run(SOTLApplication.class, args);
	}

	@Autowired
	private ReactiveRedisTemplate<String, String> template;

}
