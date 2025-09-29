package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.dto.ProgressStats;
import com.ctrlaltdelinquents.backend.dto.WeeklyStudyStats;
import com.ctrlaltdelinquents.backend.model.Topic;
import com.ctrlaltdelinquents.backend.repo.TopicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TopicControllerTest {

    @Mock
    private TopicRepository topicRepository;

    @InjectMocks
    private TopicController topicController;

    private Topic completedTopic;
    private Topic inProgressTopic;
    private String testUserId;

    @BeforeEach
    void setUp() {
        testUserId = "user123";
        
        completedTopic = new Topic();
        completedTopic.setTopicid(1);
        completedTopic.setUserid(testUserId);
        completedTopic.setTitle("Completed Topic");
        completedTopic.setDescription("A completed topic description");
        completedTopic.setStart_date(LocalDateTime.now().minusDays(5));
        completedTopic.setEnd_date(LocalDateTime.now().minusDays(1));
        completedTopic.setStatus("Completed");
        completedTopic.setCourse_code("CS101");
        completedTopic.setHours(BigDecimal.valueOf(10.5));

        inProgressTopic = new Topic();
        inProgressTopic.setTopicid(2);
        inProgressTopic.setUserid(testUserId);
        inProgressTopic.setTitle("In Progress Topic");
        inProgressTopic.setDescription("An in-progress topic description");
        inProgressTopic.setStart_date(LocalDateTime.now().minusDays(2));
        inProgressTopic.setEnd_date(null);
        inProgressTopic.setStatus("In Progress");
        inProgressTopic.setCourse_code("MATH202");
        inProgressTopic.setHours(BigDecimal.valueOf(5.0));
    }

    @Test
    void getTopicsByUserId_Success() {
        // Arrange
        List<Topic> expectedTopics = Arrays.asList(completedTopic, inProgressTopic);
        when(topicRepository.findAllByUserid(testUserId)).thenReturn(expectedTopics);

        // Act
        ResponseEntity<?> response = topicController.getTopicsByUserId(testUserId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTopics, response.getBody());
    }

    @Test
    void getTopicsByUserId_NotFound() {
        // Arrange
        when(topicRepository.findAllByUserid(testUserId)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<?> response = topicController.getTopicsByUserId(testUserId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Error: No topics found for userid: " + testUserId));
    }

    @Test
    void getTopicsByUserId_InternalServerError() {
        // Arrange
        when(topicRepository.findAllByUserid(testUserId)).thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<?> response = topicController.getTopicsByUserId(testUserId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Error: Failed to fetch topics"));
    }

    @Test
    void getCompletedTopics_Success() {
        // Arrange
        List<Topic> completedTopics = Collections.singletonList(completedTopic);
        when(topicRepository.findAllByUseridAndStatus(testUserId, "Completed")).thenReturn(completedTopics);

        // Act
        ResponseEntity<?> response = topicController.getCompletedTopics(testUserId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(completedTopics, response.getBody());
    }

    @Test
    void getCompletedTopics_NotFound() {
        // Arrange
        when(topicRepository.findAllByUseridAndStatus(testUserId, "Completed")).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<?> response = topicController.getCompletedTopics(testUserId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Error: No completed topics found for userid: " + testUserId));
    }

    @Test
    void getInProgressTopics_Success() {
        // Arrange
        List<Topic> inProgressTopics = Collections.singletonList(inProgressTopic);
        when(topicRepository.findAllByUseridAndStatus(testUserId, "In Progress")).thenReturn(inProgressTopics);

        // Act
        ResponseEntity<?> response = topicController.getInProgressTopics(testUserId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(inProgressTopics, response.getBody());
    }

    @Test
    void getInProgressTopics_NotFound() {
        // Arrange
        when(topicRepository.findAllByUseridAndStatus(testUserId, "In Progress")).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<?> response = topicController.getInProgressTopics(testUserId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Error: No in-progress topics found for userid: " + testUserId));
    }

    @Test
    void getProgressStats_Success() {
        // Arrange
        ProgressStats mockStats = new ProgressStats() {
            @Override
            public Integer getTotalHoursStudied() { return 50; }
            @Override
            public Integer getTopicsCompleted() { return 10; }
            @Override
            public Integer getCurrentStreakDays() { return 5; }
            @Override
            public Integer getStudySessionsAttended() { return 15; }
        };
        
        when(topicRepository.getProgressStatsForUser(testUserId)).thenReturn(mockStats);

        // Act
        ResponseEntity<ProgressStats> response = topicController.getProgressStats(testUserId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(50, response.getBody().getTotalHoursStudied());
        assertEquals(10, response.getBody().getTopicsCompleted());
        assertEquals(5, response.getBody().getCurrentStreakDays());
        assertEquals(15, response.getBody().getStudySessionsAttended());
    }

    @Test
    void getProgressStats_InternalServerError() {
        // Arrange
        when(topicRepository.getProgressStatsForUser(testUserId)).thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<ProgressStats> response = topicController.getProgressStats(testUserId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getWeeklyStudyHours_Success() {
        // Arrange
        WeeklyStudyStats week1 = new WeeklyStudyStats() {
            @Override
            public String getWeekLabel() { return "Week 40"; }
            @Override
            public Integer getHoursStudied() { return 12; }
        };
        
        WeeklyStudyStats week2 = new WeeklyStudyStats() {
            @Override
            public String getWeekLabel() { return "Week 41"; }
            @Override
            public Integer getHoursStudied() { return 8; }
        };
        
        List<WeeklyStudyStats> weeklyStats = Arrays.asList(week1, week2);
        when(topicRepository.getWeeklyStudyHours(testUserId)).thenReturn(weeklyStats);

        // Act
        ResponseEntity<List<WeeklyStudyStats>> response = topicController.getWeeklyStudyHours(testUserId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Week 40", response.getBody().get(0).getWeekLabel());
        assertEquals(12, response.getBody().get(0).getHoursStudied());
    }

    @Test
    void getWeeklyStudyHours_InternalServerError() {
        // Arrange
        when(topicRepository.getWeeklyStudyHours(testUserId)).thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<List<WeeklyStudyStats>> response = topicController.getWeeklyStudyHours(testUserId);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getWeeklyStudyHours_EmptyData() {
        // Arrange
        when(topicRepository.getWeeklyStudyHours(testUserId)).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<WeeklyStudyStats>> response = topicController.getWeeklyStudyHours(testUserId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }
}