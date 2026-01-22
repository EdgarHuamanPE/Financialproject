package com.tecsup.product_service.config;

import com.tecsup.product_service.factory.ResponseFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ResponseConfig {
    @Bean
    public ResponseFactory responseFactory() {
        return new ResponseFactory();
    }
}
