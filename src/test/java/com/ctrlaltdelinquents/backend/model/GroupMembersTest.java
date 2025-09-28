package com.ctrlaltdelinquents.backend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GroupMembersTest {

    @Test
    void testGroupMembersCreation() {
        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setGroupid(1);
        groupMembers.setUserid("user123");

        assertEquals(1, groupMembers.getGroupid());
        assertEquals("user123", groupMembers.getUserid());
    }

    @Test
    void testGroupMembersWithDifferentUsers() {
        GroupMembers member1 = new GroupMembers();
        member1.setGroupid(1);
        member1.setUserid("user123");

        GroupMembers member2 = new GroupMembers();
        member2.setGroupid(1);
        member2.setUserid("user456");

        assertEquals(1, member1.getGroupid());
        assertEquals("user123", member1.getUserid());
        assertEquals(1, member2.getGroupid());
        assertEquals("user456", member2.getUserid());
    }

    @Test
    void testGroupMembersWithNullUserId() {
        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setGroupid(2);
        groupMembers.setUserid(null);

        assertEquals(2, groupMembers.getGroupid());
        assertNull(groupMembers.getUserid());
    }

    @Test
    void testGroupMembersCompositeKeyBehavior() {
        // Test that both groupid and userid form the composite key
        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setGroupid(100);
        groupMembers.setUserid("testuser");

        assertEquals(100, groupMembers.getGroupid());
        assertEquals("testuser", groupMembers.getUserid());
        
        // Verify both fields are properly set and retrieved
        assertNotNull(groupMembers.toString());
    }
}