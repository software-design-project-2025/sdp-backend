package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ChatRepo extends JpaRepository<Chat, Integer> {
    
    @Query("SELECT c FROM Chat c WHERE c.user1.userid = :userid OR c.user2.userid = :userid")
    List<Chat> findByUser(@Param("userid") String userid);


    @Query("SELECT c FROM Chat c WHERE (c.user1.userid = :user1Id AND c.user2.userid = :user2Id) OR (c.user1.userid = :user2Id AND c.user2.userid = :user1Id)")
    List<Chat> findChat(@Param("user1Id") String user1Id, @Param("user2Id") String user2Id);
}