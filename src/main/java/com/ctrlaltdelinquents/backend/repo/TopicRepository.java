package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, String> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Topic t WHERE t.userid = :userid AND t.course_code = :courseCode")
    void deleteByUseridAndCourseCode(@Param("userid") String userid, @Param("courseCode") String courseCode);
}
