package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.ChatDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatDocRepository extends JpaRepository<ChatDoc, Integer> {

    /**
     * Finds all documents associated with a specific chat ID.
     * @param chatID The ID of the chat.
     * @return A list of ChatDoc entities.
     */
    List<ChatDoc> findByChatID(Integer chatID);

}