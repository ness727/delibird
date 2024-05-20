package com.megamaker.cartservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
@RequiredArgsConstructor
public class RedisConfig {
    private final Environment environment;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        String host = environment.getProperty("spring.data.redis.host");
        int port = Integer.parseInt(environment.getProperty("spring.data.redis.port"));

        return new LettuceConnectionFactory(host, port);
    }
}
