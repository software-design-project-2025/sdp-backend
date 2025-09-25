package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepo extends JpaRepository<Session, Integer> {

        // UPDATED QUERY: Find sessions where user is either creator OR member
        @Query(value = "SELECT DISTINCT s.* FROM session s " +
                        "LEFT JOIN session_members sm ON s.sessionid = sm.sessionid " +
                        "WHERE (s.creatorid = :userId OR sm.userid = :userId) " +
                        "AND s.start_time > CURRENT_TIMESTAMP " +
                        "ORDER BY s.start_time ASC", nativeQuery = true)
        List<Session> findUpcomingSessions(@Param("userId") String userId);
}