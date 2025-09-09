package com.ctrlaltdelinquents.backend.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "app") //Loads properties from application.properties that have an "app" prefix
public class SupabaseConfig {
    
    private String supabaseUrl;
    
    private String supabaseJwtSecret;
    
}