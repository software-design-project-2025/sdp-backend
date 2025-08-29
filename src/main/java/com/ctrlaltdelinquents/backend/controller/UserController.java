package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.User;
import com.ctrlaltdelinquents.backend.repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private final UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    // GET all users
    @GetMapping("/user")  
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // POST a new user
    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        return userRepo.save(user);
    }
}
