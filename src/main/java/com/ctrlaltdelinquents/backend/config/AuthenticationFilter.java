package com.ctrlaltdelinquents.backend.config;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component; 

@Component 
public class AuthenticationFilter extends OncePerRequestFilter { // OncePerRequestFilter to ensure it runs once per HTTP Request

    @Autowired
    private AuthenticationService authenticationService;

    private static final String[] EXCLUDED_PATHS = {
        "/swagger-ui/",
        "/v3/api-docs",
        "/swagger-resources/",
        "/webjars/"
    };

    @Override //@Override denotes a callback method that the framework calls automatically.
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        String path = request.getRequestURI();
        return Arrays.stream(EXCLUDED_PATHS) //Exclude these paths from API Key authentication
                .anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) //@NotNull that parameter should never be null
            throws ServletException, IOException {
        try {
            Authentication authentication = authenticationService.getAuthentication(request); //parse and validate the API key from the request (from header)
            SecurityContextHolder.getContext().setAuthentication(authentication); //If valid, stores the authentication in Spring's SecurityContextHolder so downstream components know the request is authenticated
            filterChain.doFilter(request, response); //Calls filterChain.doFilter() to pass the request to the next filter/controller
        } catch (Exception exp) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //Returns HTTP 401 Unauthorized
            response.setContentType(MediaType.APPLICATION_JSON_VALUE); //Sends the error message as JSON response
            PrintWriter writer = response.getWriter();
            writer.print(exp.getMessage()); //Writes the error message directly to the HTTP response body that gets sent back to the client
            writer.flush();
            writer.close();
        }
    }
}