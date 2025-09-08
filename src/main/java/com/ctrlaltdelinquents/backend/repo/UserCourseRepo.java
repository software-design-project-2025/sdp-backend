package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.UserCourse;
import com.ctrlaltdelinquents.backend.model.UserCourseId;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCourseRepo extends JpaRepository<UserCourse, UserCourseId> {

}
