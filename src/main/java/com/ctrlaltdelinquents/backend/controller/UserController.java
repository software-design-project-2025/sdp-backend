// package com.ctrlaltdelinquents.backend.controller;

// import com.ctrlaltdelinquents.backend.repo.UserRepository;
// import com.ctrlaltdelinquents.backend.model.User;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/users")
// public class UserController {

//     private final UserRepository userRepository;

//     // Constructor injection
//     public UserController(UserRepository userRepository) {
//         this.userRepository = userRepository;
//     }

//     @GetMapping("/all")
//     public ResponseEntity<?> getAllUsers() {
//         try {
//             List<User> users = userRepository.findAll();

//             if (users.isEmpty()) {
//                 return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                         .body("Error: No users found");
//             } else {
//                 return ResponseEntity.ok(users);
//             }

//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                     .body("Error: Failed to fetch users" + e.getMessage());
//         }
//     }

//     // GET user subjects
//     @GetMapping("/{id}/subjects")
//     public ResponseEntity<?> getUserSubjects(@PathVariable int id) {
//         List<String> optionalUserCourse = userCourseRepository.findCourseCodesByUserId(id);
//         List<Map<String, String>> subjects = new ArrayList<>();

//         if(optionalUserCourse == null || optionalUserCourse.isEmpty()) {
//             Map<String, String> response = new HashMap<>();
//             response.put("error", "No subjects found for user with id " + id);
//             return ResponseEntity.status(404).body(response);
//         }

//         for(String courseCode : optionalUserCourse) {
//             Module module = moduleRepository.findByCourseCode(courseCode);

//             if(module != null) {
//                 Map<String, String> subjectInfo = new HashMap<>();
//                 subjectInfo.put("courseCode", courseCode);
//                 subjectInfo.put("courseName", module.getCourseName());
//                 subjects.add(subjectInfo);
//             }
//         }

//         return ResponseEntity.ok(subjects);
//     }
// }