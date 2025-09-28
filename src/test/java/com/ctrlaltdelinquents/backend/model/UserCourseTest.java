package com.ctrlaltdelinquents.backend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserCourseTest {

    @Test
    void testUserCourseCreation() {
        UserCourse userCourse = new UserCourse();
        userCourse.setUserid("user123");
        userCourse.setCourseCode("CS101");

        assertEquals("user123", userCourse.getUserid());
        assertEquals("CS101", userCourse.getCourseCode());
    }

    @Test
    void testUserCourseWithMultipleEntries() {
        UserCourse uc1 = new UserCourse();
        uc1.setUserid("user123");
        uc1.setCourseCode("CS101");
        
        UserCourse uc2 = new UserCourse();
        uc2.setUserid("user123");
        uc2.setCourseCode("MATH201");
        
        UserCourse uc3 = new UserCourse();
        uc3.setUserid("user456");
        uc3.setCourseCode("CS101");

        assertEquals("CS101", uc1.getCourseCode());
        assertEquals("MATH201", uc2.getCourseCode());
        assertEquals("user456", uc3.getUserid());
        assertEquals("CS101", uc3.getCourseCode());
    }

    @Test
    void testUserCourseWithNullValues() {
        UserCourse userCourse = new UserCourse();
        userCourse.setUserid(null);
        userCourse.setCourseCode(null);

        assertNull(userCourse.getUserid());
        assertNull(userCourse.getCourseCode());
    }

    @Test
    void testUserCourseCompositeKeyBehavior() {
        UserCourse userCourse = new UserCourse();
        userCourse.setUserid("testuser");
        userCourse.setCourseCode("TEST101");
        
        // Verify both fields form the composite key
        assertEquals("testuser", userCourse.getUserid());
        assertEquals("TEST101", userCourse.getCourseCode());
        
        // Test with special characters in course code
        userCourse.setCourseCode("CS-@2024");
        assertEquals("CS-@2024", userCourse.getCourseCode());
    }
}