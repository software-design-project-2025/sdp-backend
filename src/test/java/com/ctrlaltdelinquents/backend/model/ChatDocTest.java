package com.ctrlaltdelinquents.backend.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ChatDocTest {

    @Test
    void testChatDocCreation() {
        LocalDateTime now = LocalDateTime.now();
        ChatDoc chatDoc = new ChatDoc();
        chatDoc.setDocid(1);
        chatDoc.setChatid(100);
        chatDoc.setSenderid("user123");
        chatDoc.setSent_datetime(now);
        chatDoc.setDoc("Test document content");

        assertEquals(1, chatDoc.getDocid());
        assertEquals(100, chatDoc.getChatid());
        assertEquals("user123", chatDoc.getSenderid());
        assertEquals(now, chatDoc.getSent_datetime());
        assertEquals("Test document content", chatDoc.getDoc());
    }

    @Test
    void testChatDocEquality() {
        LocalDateTime now = LocalDateTime.now();
        
        ChatDoc chatDoc1 = new ChatDoc();
        chatDoc1.setDocid(1);
        chatDoc1.setChatid(100);
        chatDoc1.setSenderid("user123");
        chatDoc1.setSent_datetime(now);
        chatDoc1.setDoc("Test document");

        ChatDoc chatDoc2 = new ChatDoc();
        chatDoc2.setDocid(1);
        chatDoc2.setChatid(100);
        chatDoc2.setSenderid("user123");
        chatDoc2.setSent_datetime(now);
        chatDoc2.setDoc("Test document");

        assertEquals(chatDoc1.getDocid(), chatDoc2.getDocid());
        assertEquals(chatDoc1.getChatid(), chatDoc2.getChatid());
        assertEquals(chatDoc1.getSenderid(), chatDoc2.getSenderid());
        assertEquals(chatDoc1.getSent_datetime(), chatDoc2.getSent_datetime());
        assertEquals(chatDoc1.getDoc(), chatDoc2.getDoc());
    }

    @Test
    void testChatDocWithNullValues() {
        ChatDoc chatDoc = new ChatDoc();
        chatDoc.setDocid(2);
        chatDoc.setChatid(200);
        
        // These should handle null values without throwing exceptions
        chatDoc.setSenderid(null);
        chatDoc.setSent_datetime(null);
        chatDoc.setDoc(null);

        assertEquals(2, chatDoc.getDocid());
        assertEquals(200, chatDoc.getChatid());
        assertNull(chatDoc.getSenderid());
        assertNull(chatDoc.getSent_datetime());
        assertNull(chatDoc.getDoc());
    }
}