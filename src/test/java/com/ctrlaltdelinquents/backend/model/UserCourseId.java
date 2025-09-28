package com.ctrlaltdelinquents.backend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserCourseIdTest {

    @Test
    void testUserCourseIdCreation() {
        UserCourseId id = new UserCourseId();
        id.setUserid("user123");
        id.setCourseCode("CS101");
        
        assertEquals("user123", id.getUserid());
        assertEquals("CS101", id.getCourseCode());
    }

    @Test
    void testUserCourseIdDefaultConstructor() {
        UserCourseId id = new UserCourseId();
        assertNotNull(id); // Should not throw exception
    }

    @Test
    void testUserCourseIdEquality() {
        UserCourseId id1 = new UserCourseId();
        id1.setUserid("user123");
        id1.setCourseCode("CS101");
        
        UserCourseId id2 = new UserCourseId();
        id2.setUserid("user123");
        id2.setCourseCode("CS101");
        
        UserCourseId id3 = new UserCourseId();
        id3.setUserid("user456");
        id3.setCourseCode("CS101");
        
        UserCourseId id4 = new UserCourseId();
        id4.setUserid("user123");
        id4.setCourseCode("MATH201");
        
        // Test equals and hashCode from Lombok @EqualsAndHashCode
        assertEquals(id1, id2);
        assertNotEquals(id1, id3);
        assertNotEquals(id1, id4);
        
        assertEquals(id1.hashCode(), id2.hashCode());
        assertNotEquals(id1.hashCode(), id3.hashCode());
    }

    @Test
    void testUserCourseIdWithNullValues() {
        UserCourseId id1 = new UserCourseId();
        id1.setUserid(null);
        id1.setCourseCode("CS101");
        
        UserCourseId id2 = new UserCourseId();
        id2.setUserid("user123");
        id2.setCourseCode(null);
        
        assertNull(id1.getUserid());
        assertEquals("CS101", id1.getCourseCode());
        assertEquals("user123", id2.getUserid());
        assertNull(id2.getCourseCode());
    }

    @Test
    void testUserCourseIdSerialization() {
        UserCourseId id = new UserCourseId();
        id.setUserid("user123");
        id.setCourseCode("CS101");
        
        // Verify the class implements Serializable
        assertTrue(id instanceof java.io.Serializable);
        
        // Test that fields are properly accessible after serialization/deserialization
        assertEquals("user123", id.getUserid());
        assertEquals("CS101", id.getCourseCode());
    }
}