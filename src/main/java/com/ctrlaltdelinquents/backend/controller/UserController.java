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
// }
