package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.dto.UserProgressStats;
import com.ctrlaltdelinquents.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String>{

    @Query("SELECT c FROM User c WHERE c.userid = :userid")
    List<User> findByUserId(@Param("userid") String userid);

    @Query(value = """
        SELECT
            COALESCE((
                SELECT SUM(T.hours)
                FROM topic AS T
                WHERE T.userID = :userId
            ), 0) AS "StudyHours",

            COALESCE((
                SELECT COUNT(DISTINCT SM2.userID)
                FROM session_members AS SM1
                INNER JOIN session_members AS SM2 ON SM1.sessionid = SM2.sessionid
                WHERE SM1.userID = :userId AND SM2.userID != :userId
            ), 0) AS "StudyPartners",

            COALESCE((
                SELECT COUNT(*)
                FROM topic AS T
                WHERE T.userID = :userId AND T.status = 'Completed'
            ), 0) AS "TopicsCompleted",

            COALESCE((
                SELECT COUNT(*)
                FROM session_members AS SM
                WHERE SM.userID = :userId
            ), 0) AS "TotalSessions"
    """, nativeQuery = true)
    UserProgressStats userGetProgressStats(@Param("userId") String userId);
}