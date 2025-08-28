package com.ctrlaltdelinquents.backend.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;

@Service
public class AuthenticationService {

    //private final AppConfig appConfig;
    private final List<String> validApiKeys;

    public AuthenticationService(AppConfig appConfig) {
        //this.appConfig = appConfig;
        // Initialize in constructor instead
        this.validApiKeys = List.of(appConfig.getApiKey());
    }


    public Authentication getAuthentication(HttpServletRequest request) {
        // Extract token from request
        String token = extractToken(request);
        
        if (token != null && validateToken(token)) {
            // Parse token and create authentication object
            String username = parseUsernameFromToken(token);
            List<SimpleGrantedAuthority> authorities = parseAuthoritiesFromToken(token);
            
            return new UsernamePasswordAuthenticationToken(username, null, authorities);
        }
        
        throw new RuntimeException("Unauthorized: Invalid API key");
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private boolean validateToken(String token) {
        // Check if token matches one of your valid API keys
        return token != null && validApiKeys.contains(token);
    }

    private String parseUsernameFromToken(String token) {
        // Implement token parsing logic
        // For JWT, you might decode and extract claims
        return "user"; // placeholder
    }

    private List<SimpleGrantedAuthority> parseAuthoritiesFromToken(String token) {
        // Extract roles/authorities from token
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}