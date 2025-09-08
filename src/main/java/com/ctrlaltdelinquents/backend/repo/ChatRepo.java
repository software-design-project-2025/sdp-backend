package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.Chat;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ChatRepo extends JpaRepository<Chat, Integer>{
    
    Optional<Chat> findById(int userid);
}
