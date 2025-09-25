package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.Degree;

import com.ctrlaltdelinquents.backend.repo.DegreeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/degree")
public class DegreeController {

   @Autowired
   private final DegreeRepo degreeRepo;

   public DegreeController(DegreeRepo degreeRepo) {this.degreeRepo = degreeRepo;}

   // GET all degrees
   @GetMapping("/all")
   public List<Degree> getAllDegree() {
       return degreeRepo.findAll();
   }
}
