package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ChatRepo extends JpaRepository<Chat, Integer> {
    
    @Query("SELECT c FROM Chat c WHERE c.user1.userid = :userid OR c.user2.userid = :userid")
    List<Chat> findByUser(@Param("userid") String userid);
}