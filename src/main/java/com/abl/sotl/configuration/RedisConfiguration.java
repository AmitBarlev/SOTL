package com.abl.sotl.configuration;

import io.lettuce.core.resource.ClientResources;
import io.opentelemetry.instrumentation.lettuce.v5_1.LettuceTelemetry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisConfiguration {

    private final RedisConfigProperties properties;

    @Bean
    public ReactiveRedisConnectionFactory connectionFactory(LettuceTelemetry telemetry) {
        LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder()
                .clientResources(
                        ClientResources.builder()
                                .tracing(telemetry.newTracing())
                                .build()
                ).build();
        RedisStandaloneConfiguration connection =
                new RedisStandaloneConfiguration(properties.getHost(), properties.getPort());
        return new LettuceConnectionFactory(connection, clientConfiguration);
    }

    @Bean
    public ReactiveRedisTemplate<String, String> template(ReactiveRedisConnectionFactory factory) {
        StringRedisSerializer serializer = new StringRedisSerializer();
        RedisSerializationContext<String, String> context = RedisSerializationContext.
                <String, String>newSerializationContext()
                .key(serializer)
                .hashKey(serializer)
                .value(serializer)
                .hashValue(serializer)
                .build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
