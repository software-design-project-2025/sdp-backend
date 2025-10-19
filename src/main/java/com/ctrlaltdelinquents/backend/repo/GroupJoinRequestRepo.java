package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.GroupJoinRequest;
import com.ctrlaltdelinquents.backend.dto.GroupJoinRequestWithDetails; // Import the new DTO
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupJoinRequestRepo extends JpaRepository<GroupJoinRequest, Integer> {

    List<GroupJoinRequest> findByUserId(String userId);

    // Fetches join requests made BY a specific user, with group titles.
    @Query("SELECT new com.ctrlaltdelinquents.backend.dto.GroupJoinRequestWithDetails(" +
            "gjr.requestId, gjr.groupId, g.title, gjr.userId, u.bio, gjr.status) " +
            "FROM GroupJoinRequest gjr " +
            "JOIN Group g ON gjr.groupId = g.groupid " +
            "JOIN User u ON gjr.userId = u.userid " +
            "WHERE gjr.userId = :userId")
    List<GroupJoinRequestWithDetails> findMyRequestsWithDetails(@Param("userId") String userId);

    // Fetches PENDING join requests FOR a specific group, with user names.
    @Query("SELECT new com.ctrlaltdelinquents.backend.dto.GroupJoinRequestWithDetails(" +
            "gjr.requestId, gjr.groupId, g.title, gjr.userId, u.bio, gjr.status) " +
            "FROM GroupJoinRequest gjr " +
            "JOIN Group g ON gjr.groupId = g.groupid " +
            "JOIN User u ON gjr.userId = u.userid " +
            "WHERE g.creatorid = :creatorId AND gjr.status = 'pending'")
    List<GroupJoinRequestWithDetails> findPendingRequestsForCreator(@Param("creatorId") String creatorId);
}
