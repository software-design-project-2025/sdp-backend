package com.ctrlaltdelinquents.backend.controller;
import com.ctrlaltdelinquents.backend.model.UserCourse;
import com.ctrlaltdelinquents.backend.repo.UserCourseRepository;
import com.ctrlaltdelinquents.backend.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("api/courses")
public class UserCourseController {
    @Autowired
    private UserCourseRepository userCourseRepository;

    public UserCourseController (UserCourseRepository userCourseRepository){
        this.userCourseRepository = userCourseRepository;
    }

   @GetMapping("/{id}")
   public ResponseEntity<?> getAllUserCourses(@PathVariable String id) {
       List<String> courses = userCourseRepository.findCourseCodesByUserId(id);

       if (courses == null || courses.isEmpty()) {
           Map<String, String> response = new HashMap<>();
           response.put("error", "User has no courses with id " + id);
           return ResponseEntity.status(404).body(response);
       } else {
           Map<String, List<String>> response = new HashMap<>();
           response.put("courses", courses);
           return ResponseEntity.ok(response);
       }
   }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsersCourses() {
        try {
            List<UserCourse> userCourses = userCourseRepository.findAll();

            if (userCourses.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error: No user courses found");
            } else {
                return ResponseEntity.ok(userCourses);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Failed to fetch user courses" + e.getMessage());
        }
    }
}
