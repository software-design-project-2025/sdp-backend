package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserCourseRepository extends JpaRepository<UserCourse, String> {
    @Query(value = "SELECT course_code FROM user_course WHERE userid = :userid", nativeQuery = true)
<<<<<<< HEAD
    List<String> findCourseCodesByUserId(@Param("userid") int userid);
    void deleteByUserid(String userid);

    List<UserCourse> findByUserid(String id);

=======
    List<String> findCourseCodesByUserId(@Param("userid") String userid);
>>>>>>> 5bf65aaa20a2a01f3e460a717f1853ddb4b9944a
}
