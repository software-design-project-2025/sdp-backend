package com.ctrlaltdelinquents.backend.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class GroupDocTest {

    @Test
    void testGroupDocCreation() {
        LocalDateTime now = LocalDateTime.now();
        GroupDoc groupDoc = new GroupDoc();
        groupDoc.setDocid(1);
        groupDoc.setGroupid(100);
        groupDoc.setSent_datetime(now);
        groupDoc.setSenderid("user123");
        groupDoc.setDoc("Group document content");

        assertEquals(1, groupDoc.getDocid());
        assertEquals(100, groupDoc.getGroupid());
        assertEquals(now, groupDoc.getSent_datetime());
        assertEquals("user123", groupDoc.getSenderid());
        assertEquals("Group document content", groupDoc.getDoc());
    }

    @Test
    void testGroupDocWithNullValues() {
        GroupDoc groupDoc = new GroupDoc();
        groupDoc.setDocid(2);
        groupDoc.setGroupid(200);
        groupDoc.setSent_datetime(null);
        groupDoc.setSenderid(null);
        groupDoc.setDoc(null);

        assertEquals(2, groupDoc.getDocid());
        assertEquals(200, groupDoc.getGroupid());
        assertNull(groupDoc.getSent_datetime());
        assertNull(groupDoc.getSenderid());
        assertNull(groupDoc.getDoc());
    }

    @Test
    void testGroupDocDateTimeComparison() {
        LocalDateTime earlier = LocalDateTime.of(2024, 1, 1, 10, 0);
        LocalDateTime later = LocalDateTime.of(2024, 1, 1, 11, 0);

        GroupDoc groupDoc = new GroupDoc();
        groupDoc.setDocid(3);
        groupDoc.setSent_datetime(earlier);

        assertTrue(groupDoc.getSent_datetime().isBefore(later));
    }
}