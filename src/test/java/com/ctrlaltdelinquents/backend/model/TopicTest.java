package com.ctrlaltdelinquents.backend.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class TopicTest {

    @Test
    void testTopicCreation() {
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 10, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 1, 1, 12, 0);
        
        Topic topic = new Topic();
        topic.setTopicid(1);
        topic.setUserid("user123");
        topic.setTitle("Java Programming");
        topic.setDescription("Learn Java basics");
        topic.setStart_date(startDate);
        topic.setEnd_date(endDate);
        topic.setStatus("Active");
        topic.setCourse_code("CS101");
        topic.setHours(new BigDecimal("2.5"));

        assertEquals(1, topic.getTopicid());
        assertEquals("user123", topic.getUserid());
        assertEquals("Java Programming", topic.getTitle());
        assertEquals("Learn Java basics", topic.getDescription());
        assertEquals(startDate, topic.getStart_date());
        assertEquals(endDate, topic.getEnd_date());
        assertEquals("Active", topic.getStatus());
        assertEquals("CS101", topic.getCourse_code());
        assertEquals(new BigDecimal("2.5"), topic.getHours());
    }

    @Test
    void testTopicWithDifferentStatuses() {
        Topic topic = new Topic();
        
        topic.setStatus("Active");
        assertEquals("Active", topic.getStatus());
        
        topic.setStatus("Completed");
        assertEquals("Completed", topic.getStatus());
        
        topic.setStatus("Pending");
        assertEquals("Pending", topic.getStatus());
        
        topic.setStatus("Cancelled");
        assertEquals("Cancelled", topic.getStatus());
    }

    @Test
    void testTopicWithNullAndEmptyValues() {
        Topic topic = new Topic();
        topic.setTopicid(2);
        topic.setUserid(null);
        topic.setTitle("");
        topic.setDescription(null);
        topic.setStart_date(null);
        topic.setEnd_date(null);
        topic.setStatus("");
        topic.setCourse_code(null);
        topic.setHours(null);

        assertEquals(2, topic.getTopicid());
        assertNull(topic.getUserid());
        assertEquals("", topic.getTitle());
        assertNull(topic.getDescription());
        assertNull(topic.getStart_date());
        assertNull(topic.getEnd_date());
        assertEquals("", topic.getStatus());
        assertNull(topic.getCourse_code());
        assertNull(topic.getHours());
    }

    @Test
    void testTopicHoursPrecision() {
        Topic topic = new Topic();
        
        // Test with different decimal precisions
        topic.setHours(new BigDecimal("1.5"));
        assertEquals(new BigDecimal("1.5"), topic.getHours());
        
        topic.setHours(new BigDecimal("0.75"));
        assertEquals(new BigDecimal("0.75"), topic.getHours());
        
        topic.setHours(new BigDecimal("10"));
        assertEquals(new BigDecimal("10"), topic.getHours());
        
        topic.setHours(new BigDecimal("0.333"));
        assertEquals(new BigDecimal("0.333"), topic.getHours());
    }

    @Test
    void testTopicDateValidation() {
        LocalDateTime startDate = LocalDateTime.of(2024, 1, 1, 9, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 1, 1, 11, 0);
        
        Topic topic = new Topic();
        topic.setStart_date(startDate);
        topic.setEnd_date(endDate);

        assertTrue(topic.getStart_date().isBefore(topic.getEnd_date()));
        
        // Test duration calculation
        long hoursBetween = java.time.Duration.between(startDate, endDate).toHours();
        assertEquals(2, hoursBetween);
    }

    @Test
    void testTopicCourseAssociation() {
        Topic topic = new Topic();
        topic.setTopicid(3);
        topic.setCourse_code("MATH201");
        topic.setTitle("Calculus");
        
        // Verify course code can be used to associate with Module entity
        assertEquals("MATH201", topic.getCourse_code());
        assertEquals("Calculus", topic.getTitle());
    }
}