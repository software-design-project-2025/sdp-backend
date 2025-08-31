package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.Module;
import com.ctrlaltdelinquents.backend.model.User;
import com.ctrlaltdelinquents.backend.model.UserCourse;
import com.ctrlaltdelinquents.backend.repo.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private DegreeRepository degreeRepository;

    @Autowired
    private UserCourseRepository userCourseRepository;
    @Autowired
    private ModuleRepository moduleRepository;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    // GET all users
    @GetMapping("/user")  
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // POST a new user
    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        return userRepo.save(user);
    }

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
}
