package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepo extends JpaRepository<Session, Integer> {

        /*
         * Example of calculating total study hours (commented out for now).
         *
         * @Query("SELECT COALESCE(SUM(EXTRACT(EPOCH FROM (s.endTime - s.startTime))) / 3600, 0) "
         * +
         * "FROM Session s JOIN SessionMembers sm ON s.sessionId = sm.sessionId " +
         * "WHERE sm.userId = :userId AND s.endTime IS NOT NULL")
         * Long calculateStudyHours(@Param("userId") String userId);
         */

        // Finds all upcoming sessions for a given user (based on creatorId).
        @Query("SELECT s FROM Session s WHERE s.creatorId = :userId AND s.startTime > CURRENT_TIMESTAMP")
        List<Session> findUpcomingSessions(@Param("userId") String userId);
}
