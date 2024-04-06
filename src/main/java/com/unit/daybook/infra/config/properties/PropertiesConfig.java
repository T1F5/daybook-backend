package com.unit.daybook.infra.config.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.unit.daybook.infra.config.jwt.JwtProperties;
// import com.unit.daybook.infra.config.redis.RedisProperties;

@EnableConfigurationProperties({
    // RedisProperties.class,
    JwtProperties.class,
})
@Configuration
public class PropertiesConfig {}
