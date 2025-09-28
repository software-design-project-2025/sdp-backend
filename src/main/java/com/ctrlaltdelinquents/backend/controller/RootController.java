package com.ctrlaltdelinquents.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public String getDeploymentMessage() {
        return "App successfully deployed on Azure";
    }
}