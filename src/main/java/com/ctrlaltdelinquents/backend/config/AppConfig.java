package com.ctrlaltdelinquents.backend.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "app") //Loads properties from application.properties that have an "app" prefix
public class AppConfig {
    private String apiKey; //Gives access to API Key from .env through application.properties
}
