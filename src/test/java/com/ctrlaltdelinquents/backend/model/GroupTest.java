package com.ctrlaltdelinquents.backend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GroupTest {

    @Test
    void testGroupCreation() {
        Group group = new Group();
        group.setGroupid(1);
        group.setTitle("Study Group");
        group.setGoal("Learn Java");
        group.setActive(true);
        group.setCreatorid("user123");

        assertEquals(1, group.getGroupid());
        assertEquals("Study Group", group.getTitle());
        assertEquals("Learn Java", group.getGoal());
        assertTrue(group.isActive());
        assertEquals("user123", group.getCreatorid());
    }

    @Test
    void testGroupInactive() {
        Group group = new Group();
        group.setGroupid(2);
        group.setTitle("Project Team");
        group.setGoal("Complete project");
        group.setActive(false);
        group.setCreatorid("user456");

        assertEquals(2, group.getGroupid());
        assertEquals("Project Team", group.getTitle());
        assertEquals("Complete project", group.getGoal());
        assertFalse(group.isActive());
        assertEquals("user456", group.getCreatorid());
    }

    @Test
    void testGroupWithNullValues() {
        Group group = new Group();
        group.setGroupid(3);
        group.setTitle(null);
        group.setGoal(null);
        group.setCreatorid(null);

        assertEquals(3, group.getGroupid());
        assertNull(group.getTitle());
        assertNull(group.getGoal());
        assertNull(group.getCreatorid());
        // Default value for boolean should be false
        assertFalse(group.isActive());
    }

    @Test
    void testGroupWithEmptyStrings() {
        Group group = new Group();
        group.setGroupid(4);
        group.setTitle("");
        group.setGoal("");
        group.setCreatorid("");

        assertEquals(4, group.getGroupid());
        assertEquals("", group.getTitle());
        assertEquals("", group.getGoal());
        assertEquals("", group.getCreatorid());
    }

    @Test
    void testGroupBooleanToggle() {
        Group group = new Group();
        group.setGroupid(5);
        
        // Test setting active to true
        group.setActive(true);
        assertTrue(group.isActive());
        
        // Test setting active to false
        group.setActive(false);
        assertFalse(group.isActive());
        
        // Test toggle back to true
        group.setActive(true);
        assertTrue(group.isActive());
    }
}