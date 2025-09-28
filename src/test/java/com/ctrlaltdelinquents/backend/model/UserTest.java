package com.ctrlaltdelinquents.backend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testUserCreation() {
        User user = new User();
        user.setUserid("user123");
        user.setRole("STUDENT");
        user.setDegreeid(1);
        user.setYearofstudy(2);
        user.setBio("Computer Science student");
        user.setStatus("ACTIVE");
        user.setProfile_picture("profile.jpg");

        assertEquals("user123", user.getUserid());
        assertEquals("STUDENT", user.getRole());
        assertEquals(1, user.getDegreeid());
        assertEquals(2, user.getYearofstudy());
        assertEquals("Computer Science student", user.getBio());
        assertEquals("ACTIVE", user.getStatus());
        assertEquals("profile.jpg", user.getProfile_picture());
    }

    @Test
    void testUserWithDifferentRoles() {
        User student = new User();
        student.setUserid("student1");
        student.setRole("STUDENT");
        
        User tutor = new User();
        tutor.setUserid("tutor1");
        tutor.setRole("TUTOR");
        
        User admin = new User();
        admin.setUserid("admin1");
        admin.setRole("ADMIN");

        assertEquals("STUDENT", student.getRole());
        assertEquals("TUTOR", tutor.getRole());
        assertEquals("ADMIN", admin.getRole());
    }

    @Test
    void testUserWithNullAndEmptyValues() {
        User user = new User();
        user.setUserid("testuser");
        user.setRole(null);
        user.setDegreeid(0);
        user.setYearofstudy(0);
        user.setBio("");
        user.setStatus(null);
        user.setProfile_picture(null);

        assertEquals("testuser", user.getUserid());
        assertNull(user.getRole());
        assertEquals(0, user.getDegreeid());
        assertEquals(0, user.getYearofstudy());
        assertEquals("", user.getBio());
        assertNull(user.getStatus());
        assertNull(user.getProfile_picture());
    }

    @Test
    void testUserYearOfStudyValidation() {
        User user = new User();
        
        // Test valid year of study values
        user.setYearofstudy(1);
        assertEquals(1, user.getYearofstudy());
        
        user.setYearofstudy(4);
        assertEquals(4, user.getYearofstudy());
        
        user.setYearofstudy(0);
        assertEquals(0, user.getYearofstudy());
        
        user.setYearofstudy(10);
        assertEquals(10, user.getYearofstudy());
    }

    @Test
    void testUserStatusValues() {
        User user = new User();
        
        user.setStatus("ACTIVE");
        assertEquals("ACTIVE", user.getStatus());
        
        user.setStatus("INACTIVE");
        assertEquals("INACTIVE", user.getStatus());
        
        user.setStatus("SUSPENDED");
        assertEquals("SUSPENDED", user.getStatus());
        
        user.setStatus("DELETED");
        assertEquals("DELETED", user.getStatus());
    }

    @Test
    void testUserProfilePicturePaths() {
        User user = new User();
        
        // Test different profile picture path formats
        user.setProfile_picture("profile.jpg");
        assertEquals("profile.jpg", user.getProfile_picture());
        
        user.setProfile_picture("/images/profiles/user123.jpg");
        assertEquals("/images/profiles/user123.jpg", user.getProfile_picture());
        
        user.setProfile_picture("https://example.com/profile.png");
        assertEquals("https://example.com/profile.png", user.getProfile_picture());
        
        user.setProfile_picture("");
        assertEquals("", user.getProfile_picture());
    }

    @Test
    void testUserDegreeAssociation() {
        User user = new User();
        user.setUserid("user123");
        user.setDegreeid(5);
        
        // Verify degree ID can be used to associate with Degree entity
        assertEquals(5, user.getDegreeid());
        
        // Test boundary values for degree ID
        user.setDegreeid(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, user.getDegreeid());
        
        user.setDegreeid(1);
        assertEquals(1, user.getDegreeid());
    }
}