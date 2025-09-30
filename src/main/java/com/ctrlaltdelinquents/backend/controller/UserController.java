package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.dto.UserProgressStats;
import com.ctrlaltdelinquents.backend.model.User;
import com.ctrlaltdelinquents.backend.repo.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
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

    @GetMapping("/stats/{userId}")
    public ResponseEntity<UserProgressStats> userGetProgressStats(@PathVariable String userId) {
        try {
            UserProgressStats stats = userRepository.userGetProgressStats(userId);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            // Log the exception details here for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody User updatedUser) {
        try {
            Optional<User> existingUserOpt = userRepository.findById(id);
            if (existingUserOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error: User with id " + id + " not found");
            }

            User existingUser = existingUserOpt.get();

            existingUser.setRole(updatedUser.getRole());
            existingUser.setDegreeid(updatedUser.getDegreeid());
            existingUser.setYearofstudy(updatedUser.getYearofstudy());
            existingUser.setBio(updatedUser.getBio());
            existingUser.setStatus(updatedUser.getStatus());
            existingUser.setProfile_picture(updatedUser.getProfile_picture());

            User savedUser = userRepository.save(existingUser);
            return ResponseEntity.ok(savedUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Failed to update user: " + e.getMessage());
        }
    }

    @PatchMapping("/patch/{id}")
    public ResponseEntity<?> patchUser(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        try {
            Optional<User> existingUserOpt = userRepository.findById(id);
            if (existingUserOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error: User with id " + id + " not found");
            }

            User existingUser = existingUserOpt.get();

            // Apply partial updates if present in the map
            if (updates.containsKey("role")) {
                existingUser.setRole((String) updates.get("role"));
            }
            if (updates.containsKey("degreeid")) {
                existingUser.setDegreeid((Integer) updates.get("degreeid"));
            }
            if (updates.containsKey("yearofstudy")) {
                existingUser.setYearofstudy((Integer) updates.get("yearofstudy"));
            }
            if (updates.containsKey("bio")) {
                existingUser.setBio((String) updates.get("bio"));
            }
            if (updates.containsKey("status")) {
                existingUser.setStatus((String) updates.get("status"));
            }
            if (updates.containsKey("profile_picture")) {
                existingUser.setProfile_picture((String) updates.get("profile_picture"));
            }

            User savedUser = userRepository.save(existingUser);
            return ResponseEntity.ok(savedUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Failed to patch user: " + e.getMessage());
        }
    }

}