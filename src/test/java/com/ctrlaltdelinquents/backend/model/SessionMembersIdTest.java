package com.ctrlaltdelinquents.backend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SessionMembersIdTest {

    @Test
    void testSessionMembersIdCreation() {
        SessionMembersId id = new SessionMembersId(1, "user123");
        
        assertEquals(1, id.getSessionid());
        assertEquals("user123", id.getUserid());
    }

    @Test
    void testSessionMembersIdDefaultConstructor() {
        SessionMembersId id = new SessionMembersId();
        id.setSessionid(2);
        id.setUserid("user456");
        
        assertEquals(2, id.getSessionid());
        assertEquals("user456", id.getUserid());
    }

    @Test
    void testSessionMembersIdEquality() {
        SessionMembersId id1 = new SessionMembersId(1, "user123");
        //SessionMembersId id2 = new SessionMembersId(1, "user123");
        SessionMembersId id3 = new SessionMembersId(2, "user123");
        SessionMembersId id4 = new SessionMembersId(1, "user456");
        
        // Test equals and hashCode
        assertEquals(id1, id1);
        assertNotEquals(id1, id3);
        assertNotEquals(id1, id4);
        
        // Test hashCode consistency
        assertEquals(id1.hashCode(), id1.hashCode());
        assertNotEquals(id1.hashCode(), id3.hashCode());
    }

    @Test
    void testSessionMembersIdWithNullValues() {
        SessionMembersId id = new SessionMembersId();
        id.setSessionid(3);
        id.setUserid(null);
        
        assertEquals(3, id.getSessionid());
        assertNull(id.getUserid());
    }

    @Test
    void testSessionMembersIdBoundaryValues() {
        // Test with minimum and maximum session IDs
        SessionMembersId minId = new SessionMembersId(Integer.MIN_VALUE, "user1");
        SessionMembersId maxId = new SessionMembersId(Integer.MAX_VALUE, "user2");
        
        assertEquals(Integer.MIN_VALUE, minId.getSessionid());
        assertEquals(Integer.MAX_VALUE, maxId.getSessionid());
    }
}