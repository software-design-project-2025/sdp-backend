package com.ctrlaltdelinquents.backend.controller;

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

    public TopicController(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
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

}
