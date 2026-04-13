package com.resto.pizzeria.web.config;

import com.resto.pizzeria.web.handler.RestControllerErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// todo:
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setErrorHandler(new RestControllerErrorHandler());
        return restTemplate;
    }
}