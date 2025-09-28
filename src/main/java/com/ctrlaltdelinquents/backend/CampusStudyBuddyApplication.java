package com.ctrlaltdelinquents.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;  // <-- ADD THIS IMPORT
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableJpaRepositories(basePackages = "com.ctrlaltdelinquents.backend.repo")
public class CampusStudyBuddyApplication {

	@GetMapping("/")
	public String message(){
		return "App successfully deployed on Azure";
	}
    
    public static void main(String[] args) {
        SpringApplication.run(CampusStudyBuddyApplication.class, args);
    }
}
