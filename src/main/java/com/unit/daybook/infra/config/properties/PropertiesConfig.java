package com.unit.daybook.infra.config.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.unit.daybook.infra.config.jwt.JwtProperties;

@EnableConfigurationProperties({
    JwtProperties.class,
})
@Configuration
public class PropertiesConfig {}
