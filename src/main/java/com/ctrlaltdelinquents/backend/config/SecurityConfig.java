package com.ctrlaltdelinquents.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated() // require authentication for all requests
            )
            .formLogin(form -> form
                .permitAll() // allow everyone to see the default login page
            )
            .logout(logout -> logout
                .permitAll() // allow logout
            );

        return http.build();
    }
}


/*To remove the login page for testing, use this : 

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // disable temporarily for testing
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/users").permitAll() // no login required
                .anyRequest().permitAll()
            );

        return http.build();
    }

} */
