// package com.ctrlaltdelinquents.backend.controller;

// import com.ctrlaltdelinquents.backend.repo.UserCourseRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import java.util.*;

// @RestController
// @RequestMapping("api/courses")
// public class UserCourseController {
//    @Autowired
//    private UserCourseRepository userCourseRepository;

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getAllUserCourses(@PathVariable int id) {
//        List<String> courses = userCourseRepository.findCourseCodesByUserId(id);

//        if (courses == null || courses.isEmpty()) {
//            Map<String, String> response = new HashMap<>();
//            response.put("error", "User has no courses with id " + id);
//            return ResponseEntity.status(404).body(response);
//        } else {
//            Map<String, List<String>> response = new HashMap<>();
//            response.put("courses", courses);
//            return ResponseEntity.ok(response);
//        }
//    }
// }
