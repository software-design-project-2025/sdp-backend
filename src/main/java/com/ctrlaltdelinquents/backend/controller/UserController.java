// src/main/java/com/ctrlaltdelinquents/backend/controller/UserController.java
package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.User;
import com.ctrlaltdelinquents.backend.repo.UserRepository;

import java.util.List;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/createUser")
    @ResponseBody
    public User createUser(@RequestBody User user) {

        //Check if user exists
        List<User> doesUserExist = userRepository.findByUserId(user.getUserid());
        if (doesUserExist.size() == 0){
            return userRepository.save(user);
        }

        return doesUserExist.get(0);
    }
}
