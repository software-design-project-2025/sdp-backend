// src/main/java/com/ctrlaltdelinquents/backend/controller/UserController.java
package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.User;
import com.ctrlaltdelinquents.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create user (called after Supabase signup)
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    // Get user by Supabase UID
    @GetMapping("/{supabaseUserId}")
    public ResponseEntity<User> getUser(@PathVariable String supabaseUserId) {
        Optional<User> user = userService.getUserBySupabaseId(supabaseUserId);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    // Update user
    @PutMapping("/{supabaseUserId}")
    public ResponseEntity<User> updateUser(
            @PathVariable String supabaseUserId,
            @RequestBody User updates) {
        return ResponseEntity.ok(userService.updateUser(supabaseUserId, updates));
    }
    
    // Delete user
    @DeleteMapping("/{supabaseUserId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String supabaseUserId) {
        userService.deleteUser(supabaseUserId);
        return ResponseEntity.noContent().build();
    }
}