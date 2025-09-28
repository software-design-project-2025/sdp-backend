package com.ctrlaltdelinquents.backend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SessionMembersTest {

    @Test
    void testSessionMembersCreation() {
        Session session = new Session();
        session.setSessionId(1);
        
        User user = new User();
        user.setUserid("user123");
        
        SessionMembers sessionMembers = new SessionMembers();
        sessionMembers.setSessionid(session);
        sessionMembers.setUserid(user);

        assertNotNull(sessionMembers.getSessionid());
        assertNotNull(sessionMembers.getUserid());
        assertEquals(1, sessionMembers.getSessionid().getSessionId());
        assertEquals("user123", sessionMembers.getUserid().getUserid());
    }

    @Test
    void testSessionMembersWithDifferentEntities() {
        Session session1 = new Session();
        session1.setSessionId(1);
        
        Session session2 = new Session();
        session2.setSessionId(2);
        
        User user1 = new User();
        user1.setUserid("user123");
        
        User user2 = new User();
        user2.setUserid("user456");
        
        SessionMembers member1 = new SessionMembers();
        member1.setSessionid(session1);
        member1.setUserid(user1);
        
        SessionMembers member2 = new SessionMembers();
        member2.setSessionid(session2);
        member2.setUserid(user2);

        assertEquals(1, member1.getSessionid().getSessionId());
        assertEquals("user123", member1.getUserid().getUserid());
        assertEquals(2, member2.getSessionid().getSessionId());
        assertEquals("user456", member2.getUserid().getUserid());
    }

    @Test
    void testSessionMembersCompositeKeyAssociation() {
        Session session = new Session();
        session.setSessionId(100);
        session.setTitle("Test Session");
        
        User user = new User();
        user.setUserid("testuser");
        user.setRole("STUDENT");
        
        SessionMembers sessionMembers = new SessionMembers();
        sessionMembers.setSessionid(session);
        sessionMembers.setUserid(user);

        // Verify the ManyToOne associations work correctly
        assertEquals("Test Session", sessionMembers.getSessionid().getTitle());
        assertEquals("STUDENT", sessionMembers.getUserid().getRole());
    }
}