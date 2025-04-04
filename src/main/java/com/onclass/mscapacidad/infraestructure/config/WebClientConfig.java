package com.onclass.mscapacidad.infraestructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${ms.technology.base-url}")
    private String baseUrl;

    @Bean(name = "technologyWebClient")
    public WebClient technologyWebClient() {
        return WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}