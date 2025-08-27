// src/main/java/com/ctrlaltdelinquents/backend/service/UserService.java
package com.ctrlaltdelinquents.backend.service;

import com.ctrlaltdelinquents.backend.model.User;
import com.ctrlaltdelinquents.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserBySupabaseId(String supabaseUserId) {
        return userRepository.findBySupabaseUserId(supabaseUserId);
    }

    public User updateUser(String supabaseUserId, User updates) {
        User existing = userRepository.findBySupabaseUserId(supabaseUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (updates.getDisplayName() != null) {
            existing.setDisplayName(updates.getDisplayName());
        }
        if (updates.getEmail() != null) {
            existing.setEmail(updates.getEmail());
        }
        
        return userRepository.save(existing);
    }
    
    public void deleteUser(String supabaseUserId) {
        User user = userRepository.findBySupabaseUserId(supabaseUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }
}