package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.Faculty;

import com.ctrlaltdelinquents.backend.repo.FacultyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/faculty")
public class FacultyController {

   @Autowired
   private final FacultyRepo facultyRepo;

   public FacultyController(FacultyRepo facultyRepo) {this.facultyRepo = facultyRepo;}

   // GET all faculties
   @GetMapping("/all")
   public List<Faculty> getAllFaculty() {
       return facultyRepo.findAll();
   }

}
