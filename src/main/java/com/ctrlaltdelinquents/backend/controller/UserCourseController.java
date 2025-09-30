package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.UserCourse;
import com.ctrlaltdelinquents.backend.repo.ModuleRepository;
import com.ctrlaltdelinquents.backend.repo.TopicRepository;

import com.ctrlaltdelinquents.backend.repo.UserCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/courses")
public class UserCourseController {
    @Autowired
    private UserCourseRepository userCourseRepository;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    public UserCourseController(
            UserCourseRepository userCourseRepository,
            ModuleRepository moduleRepository,
            TopicRepository topicRepository
    ) {
        this.userCourseRepository = userCourseRepository;
        this.moduleRepository = moduleRepository;
        this.topicRepository = topicRepository;
    }

//    public UserCourseController(UserCourseRepository userCourseRepository, ModuleRepository moduleRepository) {
//        this.userCourseRepository = userCourseRepository;
//        this.moduleRepository = moduleRepository;
//    }

//    public UserCourseController(UserCourseRepository userCourseRepository) {
//        this.userCourseRepository = userCourseRepository;
//    }
   @GetMapping("/{id}")
   public ResponseEntity<?> getAllUserCourses(@PathVariable String id) {
       List<String> courses = userCourseRepository.findCourseCodesByUserid(id);

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

    @Transactional
    @PutMapping("/put/{id}")
    public ResponseEntity<?> updateUserCourses(@PathVariable String id, @RequestBody List<String> newCourseCodes) {
        Map<String, Object> response = new HashMap<>();

        try {
            if (newCourseCodes == null || newCourseCodes.isEmpty()) {
                response.put("error", "No course codes provided in request body.");
                return ResponseEntity.badRequest().body(response);
            }

            // Step 1: Get current courses
            List<UserCourse> existingCourses = userCourseRepository.findByUserid(id);

            // Step 2: Delete related topic entries (if any) before removing user_course
            for (UserCourse course : existingCourses) {
                topicRepository.deleteByUseridAndCourseCode(course.getUserid(), course.getCourseCode());
            }

            // Step 3: Delete old user_course entries
            userCourseRepository.deleteByUserid(id);

            // Step 4: Save new courses
            List<UserCourse> newCourses = newCourseCodes.stream()
                    .map(courseCode -> {
                        UserCourse userCourse = new UserCourse();
                        userCourse.setUserid(id);
                        userCourse.setCourseCode(courseCode);
                        return userCourse;
                    }).collect(Collectors.toList());

            userCourseRepository.saveAll(newCourses);

            response.put("userid", id);
            response.put("courses", newCourseCodes);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("error", "Failed to update user courses: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/post/{id}")
    public ResponseEntity<?> addUserCourse(@PathVariable String id, @RequestBody Map<String, String> body) {
        try {
            String courseCode = body.get("courseCode");

            if (courseCode == null || courseCode.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error: courseCode is required in the request body.");
            }

            // Check if course exists in the module table already
            if (!moduleRepository.existsByCourseCode(courseCode)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error: courseCode '" + courseCode + "' does not exist in the module table.");
            }

            UserCourse userCourse = new UserCourse();
            userCourse.setUserid(id);
            userCourse.setCourseCode(courseCode);

            userCourseRepository.save(userCourse);

            Map<String, Object> response = new HashMap<>();
            response.put("userid", id);
            response.put("courseCode", courseCode);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Failed to add course to user. " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserCourse(@PathVariable String id, @RequestBody Map<String, String> body) {
        try {
            String courseCode = body.get("courseCode");

            if (courseCode == null || courseCode.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error: courseCode is required in the request body.");
            }

            Optional<UserCourse> existing = userCourseRepository.findByUseridAndCourseCode(id, courseCode);
            if (existing.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error: No user-course entry found for user '" + id + "' and courseCode '" + courseCode + "'.");
            }

            userCourseRepository.delete(existing.get());

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Successfully deleted user course.");
            response.put("userid", id);
            response.put("courseCode", courseCode);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Failed to delete user course. " + e.getMessage());
        }
    }

}