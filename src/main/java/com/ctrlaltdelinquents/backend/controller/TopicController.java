package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.Topic;
import com.ctrlaltdelinquents.backend.repo.ModuleRepo;
import com.ctrlaltdelinquents.backend.repo.ModuleRepository;
import com.ctrlaltdelinquents.backend.repo.TopicRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/topic")
public class TopicController {

    @Autowired
    private final TopicRepository topicRepo;
    @Autowired
    private ModuleRepository moduleRepository;

    public TopicController(TopicRepository topicRepo) {
        this.topicRepo = topicRepo;
    }


    @PostMapping("/new")
    public ResponseEntity<?> createTopic(@Valid @RequestBody(required = false) Topic topic, BindingResult result) {
        if (topic == null) {
            return ResponseEntity.badRequest().body("No topic data provided.");
        }

        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Invalid/Missing topic data.");
        }

        if (!moduleRepository.existsByCourseCode(topic.getCourse_code())) {
            return ResponseEntity.badRequest()
                    .body("No such course with course_code: " + topic.getCourse_code());
        }


        try {
            Topic savedTopic = topicRepo.save(topic);
            return ResponseEntity.ok(savedTopic);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create topic: " + e.getMessage());
        }
    }

}
