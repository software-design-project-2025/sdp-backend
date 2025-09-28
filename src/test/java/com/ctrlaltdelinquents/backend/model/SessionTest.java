package com.ctrlaltdelinquents.backend.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class SessionTest {

    @Test
    void testSessionCreation() {
        LocalDateTime startTime = LocalDateTime.of(2024, 1, 15, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 1, 15, 12, 0);
        
        Session session = new Session();
        session.setSessionId(1);
        session.setTitle("Study Session");
        session.setStart_time(startTime);
        session.setEnd_time(endTime);
        session.setStatus("Scheduled");
        session.setLocation("Library Room A");
        session.setDescription("Weekly study group meeting");
        session.setCreatorid("user123");
        session.setGroupid(100);

        assertEquals(1, session.getSessionId());
        assertEquals("Study Session", session.getTitle());
        assertEquals(startTime, session.getStart_time());
        assertEquals(endTime, session.getEnd_time());
        assertEquals("Scheduled", session.getStatus());
        assertEquals("Library Room A", session.getLocation());
        assertEquals("Weekly study group meeting", session.getDescription());
        assertEquals("user123", session.getCreatorid());
        assertEquals(100, session.getGroupid());
    }

    @Test
    void testSessionWithNullId() {
        LocalDateTime startTime = LocalDateTime.of(2024, 1, 15, 10, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 1, 15, 12, 0);
        
        Session session = new Session();
        // sessionId should be null initially due to auto-generation
        session.setTitle("Project Meeting");
        session.setStart_time(startTime);
        session.setEnd_time(endTime);
        session.setStatus("Planned");

        assertNull(session.getSessionId());
        assertEquals("Project Meeting", session.getTitle());
        assertEquals(startTime, session.getStart_time());
        assertEquals(endTime, session.getEnd_time());
        assertEquals("Planned", session.getStatus());
    }

    @Test
    void testSessionTimeValidation() {
        LocalDateTime startTime = LocalDateTime.of(2024, 1, 15, 14, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 1, 15, 16, 0);
        
        Session session = new Session();
        session.setStart_time(startTime);
        session.setEnd_time(endTime);

        assertTrue(session.getStart_time().isBefore(session.getEnd_time()));
        assertEquals(2, java.time.Duration.between(startTime, endTime).toHours());
    }

    @Test
    void testSessionWithDifferentStatuses() {
        Session session = new Session();
        
        // Test various status values
        session.setStatus("Scheduled");
        assertEquals("Scheduled", session.getStatus());
        
        session.setStatus("In Progress");
        assertEquals("In Progress", session.getStatus());
        
        session.setStatus("Completed");
        assertEquals("Completed", session.getStatus());
        
        session.setStatus("Cancelled");
        assertEquals("Cancelled", session.getStatus());
    }

    @Test
    void testSessionWithNullAndEmptyValues() {
        Session session = new Session();
        session.setSessionId(2);
        session.setTitle(null);
        session.setStart_time(null);
        session.setEnd_time(null);
        session.setStatus("");
        session.setLocation(null);
        session.setDescription("");
        session.setCreatorid(null);

        assertEquals(2, session.getSessionId());
        assertNull(session.getTitle());
        assertNull(session.getStart_time());
        assertNull(session.getEnd_time());
        assertEquals("", session.getStatus());
        assertNull(session.getLocation());
        assertEquals("", session.getDescription());
        assertNull(session.getCreatorid());
    }

    @Test
    void testSessionDurationCalculation() {
        LocalDateTime startTime = LocalDateTime.of(2024, 1, 15, 9, 0);
        LocalDateTime endTime = LocalDateTime.of(2024, 1, 15, 11, 30);
        
        Session session = new Session();
        session.setStart_time(startTime);
        session.setEnd_time(endTime);

        long durationMinutes = java.time.Duration.between(
            session.getStart_time(), session.getEnd_time()).toMinutes();
        
        assertEquals(150, durationMinutes); // 2.5 hours = 150 minutes
    }
}