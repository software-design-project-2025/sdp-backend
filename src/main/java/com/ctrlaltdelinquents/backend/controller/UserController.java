package com.ctrlaltdelinquents.backend.controller;
import  com.ctrlaltdelinquents.backend.repo.*;

import com.ctrlaltdelinquents.backend.model.User;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users")  // Changed to consistent endpoint
public class UserController {
    UserRepository userRepo;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
}