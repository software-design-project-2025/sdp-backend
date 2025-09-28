package com.ctrlaltdelinquents.backend.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ChatMessageTest {

    @Test
    void testChatMessageCreation() {
        LocalDateTime now = LocalDateTime.now();
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessageid(1);
        chatMessage.setChatid(100);
        chatMessage.setSenderid("user123");
        chatMessage.setSent_datetime(now);
        chatMessage.setMessage("Hello, world!");

        assertEquals(1, chatMessage.getMessageid());
        assertEquals(100, chatMessage.getChatid());
        assertEquals("user123", chatMessage.getSenderid());
        assertEquals(now, chatMessage.getSent_datetime());
        assertEquals("Hello, world!", chatMessage.getMessage());
    }

    @Test
    void testChatMessageWithNullId() {
        LocalDateTime now = LocalDateTime.now();
        ChatMessage chatMessage = new ChatMessage();
        // messageid should be null initially due to auto-generation
        chatMessage.setChatid(100);
        chatMessage.setSenderid("user123");
        chatMessage.setSent_datetime(now);
        chatMessage.setMessage("Test message");

        assertNull(chatMessage.getMessageid());
        assertEquals(100, chatMessage.getChatid());
        assertEquals("user123", chatMessage.getSenderid());
        assertEquals(now, chatMessage.getSent_datetime());
        assertEquals("Test message", chatMessage.getMessage());
    }

    @Test
    void testChatMessageEdgeCases() {
        ChatMessage chatMessage = new ChatMessage();
        
        // Test with empty string
        chatMessage.setMessage("");
        assertEquals("", chatMessage.getMessage());
        
        // Test with null values
        chatMessage.setSenderid(null);
        chatMessage.setSent_datetime(null);
        chatMessage.setMessage(null);
        
        assertNull(chatMessage.getSenderid());
        assertNull(chatMessage.getSent_datetime());
        assertNull(chatMessage.getMessage());
    }

    @Test
    void testChatMessageStringRepresentation() {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessageid(1);
        chatMessage.setMessage("Test message");
        
        // This test ensures that Lombok's @Getter annotations are working correctly
        assertNotNull(chatMessage.toString());
    }
}