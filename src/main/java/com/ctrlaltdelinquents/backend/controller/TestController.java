// Create a new file: src/main/java/com/ctrlaltdelinquents/backend/controller/TestController.java
package com.ctrlaltdelinquents.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    @GetMapping("/api/test")
    public String test() {
        return "Backend is working! Database not required for this endpoint.";
    }
    
    @GetMapping("/api/health")
    public String health() {
        return "Server is healthy!";
    }
}