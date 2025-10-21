package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.Topic;
import com.ctrlaltdelinquents.backend.repo.ModuleRepository;
import com.ctrlaltdelinquents.backend.repo.TopicRepository;
import com.ctrlaltdelinquents.backend.dto.ProgressStats;
import com.ctrlaltdelinquents.backend.dto.WeeklyStudyStats;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/topic")
@CrossOrigin(origins = "http://localhost:4200")
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
        try {
            List<Topic> topics = topicRepository.findAllByUserid(userid);
            if (topics.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error: No topics found for userid: " + userid);
            }
            return ResponseEntity.ok(topics);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Failed to fetch topics: " + e.getMessage());
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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/weekly-hours/{userId}")
    public ResponseEntity<List<WeeklyStudyStats>> getWeeklyStudyHours(@PathVariable String userId) {
        try {
            List<WeeklyStudyStats> weeklyData = topicRepository.getWeeklyStudyHours(userId);
            return ResponseEntity.ok(weeklyData);
        } catch (Exception e) {
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

        try {
            Topic savedTopic = topicRepository.save(topic);
            return ResponseEntity.ok(savedTopic);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Failed to create topic: " + e.getMessage());
        }
    }

    // Get number of topics completed in last 7 days
    @GetMapping("/topics/num-topics")
    public TopicCountResponse getNumberOfTopics(@RequestParam String userId) {
        Integer topicCount = topicRepository.countTopicsCompletedLast7Days(userId);
        int count = topicCount != null ? topicCount : 0;
        return new TopicCountResponse(userId, count);
    }

    // Simple Response DTO
    public static class TopicCountResponse {
        private String userId;
        private int numTopics;

        public TopicCountResponse() {
        }

        public TopicCountResponse(String userId, int numTopics) {
            this.userId = userId;
            this.numTopics = numTopics;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public int getNumTopics() {
            return numTopics;
        }

        public void setNumTopics(int numTopics) {
            this.numTopics = numTopics;
        }
    }
}
