package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.ChatMessage;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ChatMessageRepo extends JpaRepository<ChatMessage, Integer>{

    @Query("SELECT c FROM ChatMessage c WHERE c.chatid = :chatid")
    List<ChatMessage> findByChat(@Param("chatid") int chatid);

    // Update only status column
    @Modifying
    @Transactional
    @Query("UPDATE ChatMessage c SET c.read_status = :read_status WHERE c.messageid = :messageid")
    void updateStatus(@Param("messageid") int messageid, @Param("read_status") boolean read_status);
}
