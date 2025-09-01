package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.UserCourse;

import com.ctrlaltdelinquents.backend.repo.UserCourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/usercourse")
public class UserCourseController {

    @Autowired
    private final UserCourseRepo userCourseRepo;

    public UserCourseController(UserCourseRepo userCourseRepo) {this.userCourseRepo = userCourseRepo;}

    // GET all modules
    @GetMapping("/all")
    public List<UserCourse> getAllUserCourse() {
        return userCourseRepo.findAll();
    }

}
