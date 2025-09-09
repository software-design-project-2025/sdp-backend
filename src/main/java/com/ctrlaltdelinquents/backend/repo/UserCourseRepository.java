package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.UserCourse;
import com.ctrlaltdelinquents.backend.model.UserCourseId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCourseRepository extends JpaRepository<UserCourse, UserCourseId> {
    @Query(value = "SELECT course_code FROM user_course WHERE userid = :userid", nativeQuery = true)
    List<String> findCourseCodesByUserId(@Param("userid") int userid);
}
//