package com.bezkoder.spring.r2dbc.mysql.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("tutorial-api")
                .pathsToMatch("/api/**")  // Define the API base path
                .build();
    }
}
