package com.ctrlaltdelinquents.backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ctrlaltdelinquents.backend.model.Module;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ModuleRepository extends JpaRepository<Module, String> {
    @Query(value = "SELECT * FROM module WHERE course_code = :courseCode", nativeQuery = true)
    Module findByCourseCode(@Param("courseCode") String courseCode);
}
