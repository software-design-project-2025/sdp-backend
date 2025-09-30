package com.ctrlaltdelinquents.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;  // <-- ADD THIS IMPORT

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.ctrlaltdelinquents.backend.repo")
public class CampusStudyBuddyApplication {
    public static void main(String[] args) {
        SpringApplication.run(CampusStudyBuddyApplication.class, args);
    }
}
