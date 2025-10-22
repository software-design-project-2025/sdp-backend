package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.GroupDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupDocRepo extends JpaRepository<GroupDoc, Integer> {

    /**
     * Finds all documents associated with a specific chat ID.
     * @param groupID The ID of the chat.
     * @return A list of ChatDoc entities.
     */
    List<GroupDoc> findByGroupID(Integer groupID);

}