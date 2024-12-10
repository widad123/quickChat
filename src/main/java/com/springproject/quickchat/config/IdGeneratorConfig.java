package com.springproject.quickchat.config;

import com.springproject.quickchat.utils.IdGenerator;
import com.springproject.quickchat.utils.UuidToLongGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdGeneratorConfig {

    @Bean
    public IdGenerator idGenerator() {
        return new UuidToLongGenerator();
    }
}
