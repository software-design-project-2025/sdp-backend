package com.ctrlaltdelinquents.backend.repo;


import com.ctrlaltdelinquents.backend.dto.ProgressStats;
import com.ctrlaltdelinquents.backend.dto.WeeklyStudyStats;
import com.ctrlaltdelinquents.backend.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Repository
public interface TopicRepository extends JpaRepository<Topic, String> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Topic t WHERE t.userid = :userid AND t.course_code = :courseCode")
    void deleteByUseridAndCourseCode(@Param("userid") String userid, @Param("courseCode") String courseCode);
    List<Topic> findAllByUserid(String userid);

    List<Topic> findAllByUseridAndStatus(String userid, String status);

    @Query(value = """
                WITH DateGroups AS (
                    -- Group consecutive dates together.
                    SELECT
                        completion_date,
                        (completion_date - (ROW_NUMBER() OVER (ORDER BY completion_date))::int * INTERVAL '1 day') AS streak_group
                    FROM (
                        -- First, get all unique days the user completed a topic.
                        SELECT DISTINCT CAST(end_date AS DATE) AS completion_date
                        FROM topic
                        WHERE userid = :userId AND status = 'Completed'
                    ) AS DistinctDates
                ),
                LatestStreak AS (
                    -- Count the number of days in the most recent streak group.
                    SELECT COUNT(*) as streak_length
                    FROM DateGroups
                    WHERE streak_group = (
                        -- Find the streak group associated with the very last completion date.
                        SELECT streak_group FROM DateGroups ORDER BY completion_date DESC LIMIT 1
                    )
                ),
                CurrentStreak AS (
                    -- Final check: if the last activity was not today or yesterday, the streak is broken (0).
                    SELECT
                        CASE
                            WHEN NOT EXISTS (SELECT 1 FROM DateGroups) THEN 0
                            WHEN (SELECT MAX(completion_date) FROM DateGroups) >= (CURRENT_DATE - INTERVAL '1 day')
                            THEN (SELECT streak_length FROM LatestStreak)
                            ELSE 0
                        END AS current_streak
                )
                -- Final SELECT statement to combine all the metrics into a single row.
                SELECT
                    COALESCE((SELECT SUM(hours) FROM topic WHERE userid = :userId), 0) AS totalHoursStudied,
                    COALESCE((SELECT COUNT(*) FROM topic WHERE userid = :userId AND status = 'Completed'), 0) AS topicsCompleted,
                    COALESCE((SELECT current_streak FROM CurrentStreak), 0) AS currentStreakDays,
                    COALESCE((SELECT COUNT(*) FROM session_members WHERE userid = :userId), 0) AS studySessionsAttended
            """, nativeQuery = true)
    ProgressStats getProgressStatsForUser(@Param("userId") String userId);

    @Query(value = """
            WITH Weeks AS (
                SELECT generate_series(
                    date_trunc('week', CURRENT_DATE) - INTERVAL '5 weeks',
                    date_trunc('week', CURRENT_DATE),
                    '1 week'
                )::date AS week_start_date
            ),
            StudyData AS (
                SELECT
                    date_trunc('week', end_date)::date AS week_start_date,
                    SUM(hours) as total_hours
                FROM
                    topic
                WHERE
                    userid = :userId
                    AND status = 'Completed'
                    AND end_date >= (date_trunc('week', CURRENT_DATE) - INTERVAL '5 weeks')
                GROUP BY
                    date_trunc('week', end_date)
            )
            SELECT
                CONCAT('Week ', CAST(EXTRACT(WEEK FROM Weeks.week_start_date) AS TEXT)) AS week_label,
                COALESCE(StudyData.total_hours, 0) AS hours_studied
            FROM
                Weeks
            LEFT JOIN
                StudyData ON Weeks.week_start_date = StudyData.week_start_date
            ORDER BY
                Weeks.week_start_date ASC
            """, nativeQuery = true)
    List<WeeklyStudyStats> getWeeklyStudyHours(@Param("userId") String userId);

    // Count topics completed in last 7 days for a user
    @Query(value = "SELECT COUNT(*) FROM topic WHERE userid = :userId AND status = 'Completed' " +
            "AND end_date >= CURRENT_DATE - INTERVAL '7 days'", nativeQuery = true)
    Integer countTopicsCompletedLast7Days(@Param("userId") String userId);

}
