package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.Module;
import com.ctrlaltdelinquents.backend.model.User;
import com.ctrlaltdelinquents.backend.repo.*;
import com.ctrlaltdelinquents.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/users")  // Changed to consistent endpoint
public class UserController {
    
    private final UserService userService;  
    
    @Autowired
    private final UserRepo userRepo;
    
    //@Autowired
    //private FacultyRepository facultyRepository;
    
    //@Autowired
    //private DegreeRepository degreeRepository;
    
    @Autowired
    UserCourseRepository userCourseRepository;
    
    @Autowired
    ModuleRepository moduleRepository;

    public UserController(UserService userService, UserRepo userRepo) {
        this.userService = userService;
        this.userRepo = userRepo;
    }

 
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    // Get user by Supabase UID
    @GetMapping("/supabase/{supabaseUserId}")
    public ResponseEntity<User> getUserBySupabaseId(@PathVariable String supabaseUserId) {
        Optional<User> user = userService.getUserBySupabaseId(supabaseUserId);
        return user.map(ResponseEntity::ok)
                  .orElse(ResponseEntity.notFound().build());
    }

    // Update user by Supabase UID
    @PutMapping("/supabase/{supabaseUserId}")
    public ResponseEntity<User> updateUserBySupabaseId(
            @PathVariable String supabaseUserId,
            @RequestBody User updates) {
        return ResponseEntity.ok(userService.updateUser(supabaseUserId, updates));
    }
    
    // Delete user by Supabase UID
    @DeleteMapping("/supabase/{supabaseUserId}")
    public ResponseEntity<Void> deleteUserBySupabaseId(@PathVariable String supabaseUserId) {
        userService.deleteUser(supabaseUserId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping  
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // GET user by database ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        Optional<User> optionalUser = userRepo.findById(id);

        if (optionalUser.isPresent()) {
            return ResponseEntity.ok(optionalUser.get());
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("error", "User not found with id " + id);
            return ResponseEntity.status(404).body(response);
        }
    }

    // GET user subjects
    @GetMapping("/{id}/subjects")
    public ResponseEntity<?> getUserSubjects(@PathVariable int id) {
        List<String> optionalUserCourse = userCourseRepository.findCourseCodesByUserId(id);
        List<Map<String, String>> subjects = new ArrayList<>();

        if(optionalUserCourse == null || optionalUserCourse.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("error", "No subjects found for user with id " + id);
            return ResponseEntity.status(404).body(response);
        }

        for(String courseCode : optionalUserCourse) {
            Module module = moduleRepository.findByCourseCode(courseCode);

            if(module != null) {
                Map<String, String> subjectInfo = new HashMap<>();
                subjectInfo.put("courseCode", courseCode);
                subjectInfo.put("courseName", module.getCourseName());
                subjects.add(subjectInfo);
            }
        }

        return ResponseEntity.ok(subjects);
    }
}