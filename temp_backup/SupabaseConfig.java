package com.ctrlaltdelinquents.backend.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class SupabaseConfig {
    
    @Value("${supabase.url}")
    private String supabaseUrl;
    
    @Value("${supabase.jwt.secret}") // Changed from supabase.key
    private String supabaseJwtSecret;
    
    public String getSupabaseUrl() {
        return supabaseUrl;
    }
    
    public String getSupabaseJwtSecret() {
        return supabaseJwtSecret;
    }

}