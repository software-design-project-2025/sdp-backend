package com.ctrlaltdelinquents.backend.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

class ChatTest {

    private Chat chat;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        chat = new Chat();
        
        user1 = new User();
        user1.setUserid("user123");
        user1.setRole("student");
        user1.setBio("First user");
        
        user2 = new User();
        user2.setUserid("user456");
        user2.setRole("teacher");
        user2.setBio("Second user");
    }

    @Test
    @DisplayName("Should create a Chat with default values")
    void createChat_withDefaults() {
        assertThat(chat).isNotNull();
        assertThat(chat.getChatid()).isEqualTo(0); // Default int value
        assertThat(chat.getUser1()).isNull();
        assertThat(chat.getUser2()).isNull();
    }

    @Test
    @DisplayName("Should set and get chatid")
    void setChatid_shouldSetAndGet() {
        chat.setChatid(42);
        
        assertThat(chat.getChatid()).isEqualTo(42);
    }

    @Test
    @DisplayName("Should set and get user1")
    void setUser1_shouldSetAndGet() {
        chat.setUser1(user1);
        
        assertThat(chat.getUser1()).isEqualTo(user1);
        assertThat(chat.getUser1().getUserid()).isEqualTo("user123");
        assertThat(chat.getUser1().getRole()).isEqualTo("student");
    }

    @Test
    @DisplayName("Should set and get user2")
    void setUser2_shouldSetAndGet() {
        chat.setUser2(user2);
        
        assertThat(chat.getUser2()).isEqualTo(user2);
        assertThat(chat.getUser2().getUserid()).isEqualTo("user456");
        assertThat(chat.getUser2().getRole()).isEqualTo("teacher");
    }

    @Test
    @DisplayName("Should handle null users")
    void setNullUsers_shouldHandleGracefully() {
        chat.setUser1(null);
        chat.setUser2(null);
        
        assertThat(chat.getUser1()).isNull();
        assertThat(chat.getUser2()).isNull();
    }

    @Test
    @DisplayName("Should allow same user for both user1 and user2")
    void setSameUser_shouldWork() {
        chat.setUser1(user1);
        chat.setUser2(user1);
        
        assertThat(chat.getUser1()).isEqualTo(user1);
        assertThat(chat.getUser2()).isEqualTo(user1);
        assertThat(chat.getUser1()).isEqualTo(chat.getUser2());
    }

    @Test
    @DisplayName("Should create complete chat with both users")
    void createCompleteChat() {
        chat.setChatid(100);
        chat.setUser1(user1);
        chat.setUser2(user2);
        
        assertThat(chat.getChatid()).isEqualTo(100);
        assertThat(chat.getUser1()).isEqualTo(user1);
        assertThat(chat.getUser2()).isEqualTo(user2);
        assertThat(chat.getUser1().getUserid()).isEqualTo("user123");
        assertThat(chat.getUser2().getUserid()).isEqualTo("user456");
    }

    @Test
    @DisplayName("Should test equals and hashCode behavior")
    void testEqualsAndHashCode() {
        Chat chat1 = new Chat();
        chat1.setChatid(1);
        chat1.setUser1(user1);
        chat1.setUser2(user2);
        
        Chat chat2 = new Chat();
        chat2.setChatid(1);
        chat2.setUser1(user1);
        chat2.setUser2(user2);
        
        Chat chat3 = new Chat();
        chat3.setChatid(2);
        chat3.setUser1(user1);
        chat3.setUser2(user2);
        
        // Note: This tests object identity, not logical equality
        // since Chat doesn't override equals/hashCode
        assertThat(chat1).isNotEqualTo(chat2);
        assertThat(chat1).isNotEqualTo(chat3);
    }

    @Test
    @DisplayName("Should handle user reassignment")
    void reassignUsers_shouldWork() {
        // Initially set users
        chat.setUser1(user1);
        chat.setUser2(user2);
        
        assertThat(chat.getUser1()).isEqualTo(user1);
        assertThat(chat.getUser2()).isEqualTo(user2);
        
        // Swap users
        chat.setUser1(user2);
        chat.setUser2(user1);
        
        assertThat(chat.getUser1()).isEqualTo(user2);
        assertThat(chat.getUser2()).isEqualTo(user1);
    }
}