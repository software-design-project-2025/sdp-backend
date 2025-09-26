// src/main/java/com/ctrlaltdelinquents/backend/controller/UserController.java
package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.User;
import com.ctrlaltdelinquents.backend.repo.UserRepository;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();

            if (users.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error: No users found");
            } else {
                return ResponseEntity.ok(users);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Failed to fetch users" + e.getMessage());
        }
    }

    @GetMapping("/{userid}")
    public ResponseEntity<?> getUserById(@PathVariable String userid) {
        try {
            List<User> user = userRepository.findByUserId(userid);
            if(user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error: User with id " + userid + "not found");
            }
            else{
                return ResponseEntity.ok(user);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Failed to fetch user "+ userid+ " by ID" + e.getMessage());
        }

    }
}
