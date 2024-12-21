package com.abl.sotl.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties("redis")
@NoArgsConstructor
@AllArgsConstructor
public class RedisConfigProperties {

    private String host;
    private int port;
}
