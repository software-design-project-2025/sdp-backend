package com.yourorg.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("https://ambitious-sand-0f3b82603.1.azurestaticapps.net")
            .allowedMethods("*")
            .allowedHeaders("*");
    }
}
