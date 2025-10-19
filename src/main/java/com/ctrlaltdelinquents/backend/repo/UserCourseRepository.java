package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserCourseRepository extends JpaRepository<UserCourse, String> {
    @Query(value = "SELECT course_code FROM user_course WHERE userid = :userid", nativeQuery = true)
    List<String> findCourseCodesByUserid(@Param("userid") int userid);
    void deleteByUserid(String userid);

    List<UserCourse> findByUserid(String id);

    List<UserCourse> findCourseCodesByUserid(@Param("userid") String userid);

    Optional<UserCourse> findByUseridAndCourseCode(String id, String courseCode);
}
