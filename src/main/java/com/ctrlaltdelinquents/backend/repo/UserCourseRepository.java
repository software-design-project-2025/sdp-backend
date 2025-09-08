package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCourseRepository extends JpaRepository<UserCourse, Integer> {
    @Query(value = "SELECT course_code FROM user_course WHERE userid = :userid", nativeQuery = true)
    List<String> findCourseCodesByUserId(@Param("userid") int userid);
}
