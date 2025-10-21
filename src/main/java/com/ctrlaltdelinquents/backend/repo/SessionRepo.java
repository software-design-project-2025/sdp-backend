package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.ctrlaltdelinquents.backend.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SessionRepo extends JpaRepository<Session, Integer>, JpaSpecificationExecutor<Session> {
    List<Session> findByCreatorid(String creatorId);
    List<Session> findByGroupid(int groupId);

    // UPDATED QUERY: Find sessions where user is either creator OR member
    @Query(value = "SELECT s.* FROM session s " +
            "WHERE s.start_time > CURRENT_TIMESTAMP " +
            "AND (s.creatorid = :userId OR EXISTS ( " +
            "  SELECT 1 FROM session_members sm " +
            "  WHERE sm.sessionid = s.sessionid AND sm.userid = :userId " +
            ")) " +
            "ORDER BY s.start_time ASC", nativeQuery = true)
    List<Session> findUpcomingSessions(@Param("userId") String userId);

    // Get total study hours for a user in the past 7 days
    // only counts completed sessions user was part of
    @Query(value = "SELECT COALESCE(SUM(ABS(EXTRACT(EPOCH FROM (s.end_time - s.start_time))) / 3600), 0) " +
                    "FROM session s " +
                    "JOIN session_members sm ON s.sessionid = sm.sessionid " + // Changed to INNER JOIN (must be
                                                                               // member)
                    "WHERE sm.userid = :userId " + // User must be a session member
                    "AND s.end_time IS NOT NULL " +
                    "AND s.status = 'completed' " + // Session must be marked as completed
                    "AND s.end_time <= CURRENT_TIMESTAMP " + // Session must be in the past
                    "AND s.start_time >= CURRENT_DATE - INTERVAL '7 days'", nativeQuery = true)
    Double getActualStudyHoursLast7Days(@Param("userId") String userId);

    // Get number of sessions attended by user in past 7 days
    @Query(value = "SELECT COUNT(*) FROM session_members sm " +
                    "JOIN session s ON sm.sessionid = s.sessionid " +
                    "WHERE sm.userid = :userId " +
                    "AND s.end_time IS NOT NULL " +
                    "AND s.status = 'completed' " +
                    "AND s.end_time >= CURRENT_DATE - INTERVAL '7 days'", nativeQuery = true)
    Integer countSessionsAttendedLast7Days(@Param("userId") String userId);

    // "Past Sessions" Tab
    @Query(value = "SELECT DISTINCT s.* FROM session s " +
            "LEFT JOIN session_members sm ON s.sessionid = sm.sessionid " +
            "WHERE (s.creatorid = :userId OR sm.userid = :userId) " +
            "AND s.status = 'completed' " + // Only show completed sessions
            "AND s.start_time < CURRENT_TIMESTAMP " +
            "ORDER BY s.start_time DESC", nativeQuery = true)
    List<Session> findPastSessions(@Param("userId") String userId);

    // --- QUERY FOR JOINING (Overlap Check) ---

    // Finds sessions a user is in (as member or creator) that conflict with a new time range
    @Query(value = "SELECT s.* FROM session s " +
            "LEFT JOIN session_members sm ON s.sessionid = sm.sessionid " +
            "WHERE (s.creatorid = :userId OR sm.userid = :userId) " +
            "AND s.status != 'completed' " + // Don't check against completed sessions
            "AND (s.start_time < :newEndTime AND s.end_time > :newStartTime)", nativeQuery = true)
    List<Session> findOverlappingSessions(@Param("userId") String userId,
                                          @Param("newStartTime") LocalDateTime newStartTime,
                                          @Param("newEndTime") LocalDateTime newEndTime);
}
