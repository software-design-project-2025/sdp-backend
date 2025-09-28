package com.ctrlaltdelinquents.backend.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class GroupMessageTest {

    @Test
    void testGroupMessageCreation() {
        LocalDateTime now = LocalDateTime.now();
        GroupMessage groupMessage = new GroupMessage();
        groupMessage.setMessageid(1);
        groupMessage.setGroupid(100);
        groupMessage.setSent_datetime(now);
        groupMessage.setSenderid("user123");
        groupMessage.setMessage("Hello group!");

        assertEquals(1, groupMessage.getMessageid());
        assertEquals(100, groupMessage.getGroupid());
        assertEquals(now, groupMessage.getSent_datetime());
        assertEquals("user123", groupMessage.getSenderid());
        assertEquals("Hello group!", groupMessage.getMessage());
    }

    @Test
    void testGroupMessageWithLongContent() {
        LocalDateTime now = LocalDateTime.now();
        String longMessage = "This is a very long message ".repeat(50);
        
        GroupMessage groupMessage = new GroupMessage();
        groupMessage.setMessageid(2);
        groupMessage.setGroupid(200);
        groupMessage.setSent_datetime(now);
        groupMessage.setSenderid("user456");
        groupMessage.setMessage(longMessage);

        assertEquals(longMessage, groupMessage.getMessage());
        assertTrue(groupMessage.getMessage().length() > 100);
    }

    @Test
    void testGroupMessageWithNullValues() {
        GroupMessage groupMessage = new GroupMessage();
        groupMessage.setMessageid(3);
        groupMessage.setGroupid(300);
        groupMessage.setSent_datetime(null);
        groupMessage.setSenderid(null);
        groupMessage.setMessage(null);

        assertEquals(3, groupMessage.getMessageid());
        assertEquals(300, groupMessage.getGroupid());
        assertNull(groupMessage.getSent_datetime());
        assertNull(groupMessage.getSenderid());
        assertNull(groupMessage.getMessage());
    }

    @Test
    void testGroupMessageDateTimeOrdering() {
        LocalDateTime first = LocalDateTime.of(2024, 1, 1, 10, 0);
        LocalDateTime second = LocalDateTime.of(2024, 1, 1, 10, 30);

        GroupMessage message1 = new GroupMessage();
        message1.setMessageid(1);
        message1.setSent_datetime(first);

        GroupMessage message2 = new GroupMessage();
        message2.setMessageid(2);
        message2.setSent_datetime(second);

        assertTrue(message1.getSent_datetime().isBefore(message2.getSent_datetime()));
    }
}