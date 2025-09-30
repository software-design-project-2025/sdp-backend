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
import com.ctrlaltdelinquents.backend.dto.ProgressStats;
import com.ctrlaltdelinquents.backend.dto.WeeklyStudyStats;
import com.ctrlaltdelinquents.backend.model.Topic;
import com.ctrlaltdelinquents.backend.repo.TopicRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/topic")
public class TopicController {
    private final TopicRepository topicRepository;
    
    @Autowired
    private ModuleRepository moduleRepository;


    public TopicController(TopicRepository topicRepository, ModuleRepository moduleRepository) {
        this.topicRepository = topicRepository;
        this.moduleRepository = moduleRepository;
    }

    @GetMapping("/{userid}")
    public ResponseEntity<?> getTopicsByUserId(@PathVariable String userid) {
        try{
            List<Topic> topics = topicRepository.findAllByUserid(userid);

            if(topics.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error: No topics found for userid: " + userid);
            }
            return ResponseEntity.ok(topics);
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Failed to fetch topics: "+ e.getMessage());
        }
    }

    @GetMapping("/{userid}/completed")
    public ResponseEntity<?> getCompletedTopics(@PathVariable String userid) {
        try {
            List<Topic> topics = topicRepository.findAllByUseridAndStatus(userid, "Completed");

            if (topics.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error: No completed topics found for userid: " + userid);
            }

            return ResponseEntity.ok(topics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Failed to fetch completed topics: " + e.getMessage());
        }
    }

    @GetMapping("/{userid}/in-progress")
    public ResponseEntity<?> getInProgressTopics(@PathVariable String userid) {
        try {
            List<Topic> topics = topicRepository.findAllByUseridAndStatus(userid, "In Progress");

            if (topics.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error: No in-progress topics found for userid: " + userid);
            }

            return ResponseEntity.ok(topics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Failed to fetch in-progress topics: " + e.getMessage());
        }
    }

    @GetMapping("/stats/{userId}")
    public ResponseEntity<ProgressStats> getProgressStats(@PathVariable String userId) {
        try {
            ProgressStats stats = topicRepository.getProgressStatsForUser(userId);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            // Log the exception details here for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/weekly-hours/{userId}")
    public ResponseEntity<List<WeeklyStudyStats>> getWeeklyStudyHours(@PathVariable String userId) {
        try {
            List<WeeklyStudyStats> weeklyData = topicRepository.getWeeklyStudyHours(userId);
            return ResponseEntity.ok(weeklyData);
        } catch (Exception e) {
            // It's good practice to log the exception
            // e.g., log.error("Error fetching weekly study hours for user: {}", userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
            Topic savedTopic = topicRepository.save(topic);
            return ResponseEntity.ok(savedTopic);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create topic: " + e.getMessage());
        }
    }
}
