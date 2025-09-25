// src/main/java/com/ctrlaltdelinquents/backend/repository/UserRepository.java
package com.ctrlaltdelinquents.backend.repository;

import com.ctrlaltdelinquents.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findBySupabaseUserId(String supabaseUserId);
    Optional<User> findByEmail(String email);
}