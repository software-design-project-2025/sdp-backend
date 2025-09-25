package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.Chat;
import com.ctrlaltdelinquents.backend.model.User;
import com.ctrlaltdelinquents.backend.repo.ChatRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ChatControllerTest {

    private ChatRepo chatRepo;
    private ChatController chatController;

    @BeforeEach
    void setUp() {
        chatRepo = mock(ChatRepo.class);
        chatController = new ChatController(chatRepo);
    }

    @Test
    @DisplayName("Should return chat when chat with given id exists")
    void getChatsByUser_returnsChat_whenChatExists() {
        User user1 = new User();
        user1.setId(1L); // Using Long ID instead of String userid
        user1.setSupabaseUserId("supabase-1"); // Using supabaseUserId instead of userid
        user1.setEmail("user1@test.com");
        
        User user2 = new User();
        user2.setId(2L);
        user2.setSupabaseUserId("supabase-2");
        user2.setEmail("user2@test.com");

        Chat chat = new Chat();
        chat.setChatid(10);
        chat.setUser1(user1);
        chat.setUser2(user2);

        // Assuming findByUser method uses supabaseUserId, not the numeric ID
        when(chatRepo.findByUser("supabase-1")).thenReturn(List.of(chat));

        List<Chat> result = chatController.getChatsByUser("supabase-1");

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getChatid()).isEqualTo(10);
        assertThat(result.get(0).getUser1().getSupabaseUserId()).isEqualTo("supabase-1");
        assertThat(result.get(0).getUser2().getSupabaseUserId()).isEqualTo("supabase-2");
        verify(chatRepo, times(1)).findByUser("supabase-1");
    }

    @Test
    @DisplayName("Should return empty when chat with given id does not exist")
    void getChatsByUser_returnsEmpty_whenChatDoesNotExist() {
        when(chatRepo.findByUser("non-existent-id")).thenReturn(List.of());

        List<Chat> result = chatController.getChatsByUser("non-existent-id");

        assertThat(result).isEmpty();
        verify(chatRepo, times(1)).findByUser("non-existent-id");
    }

    @Test
    @DisplayName("Should save and return chat when creating new chat")
    void createChat_savesAndReturnsChat() {
        User user1 = new User();
        user1.setId(1L);
        user1.setSupabaseUserId("supabase-1");
        user1.setEmail("user1@test.com");
        
        User user2 = new User();
        user2.setId(2L);
        user2.setSupabaseUserId("supabase-2");
        user2.setEmail("user2@test.com");

        Chat chat = new Chat();
        chat.setChatid(20);
        chat.setUser1(user1);
        chat.setUser2(user2);

        when(chatRepo.save(chat)).thenReturn(chat);

        Chat saved = chatController.createChat(chat);

        assertThat(saved.getChatid()).isEqualTo(20);
        assertThat(saved.getUser1().getSupabaseUserId()).isEqualTo("supabase-1");
        assertThat(saved.getUser2().getSupabaseUserId()).isEqualTo("supabase-2");
        verify(chatRepo, times(1)).save(chat);
    }

    @Test
    @DisplayName("Should pass correct entity to repository when saving chat")
    void createChat_passesCorrectEntityToRepo() {
        User user1 = new User();
        user1.setId(5L);
        user1.setSupabaseUserId("supabase-5");
        user1.setEmail("user5@test.com");
        
        User user2 = new User();
        user2.setId(6L);
        user2.setSupabaseUserId("supabase-6");
        user2.setEmail("user6@test.com");

        Chat chat = new Chat();
        chat.setChatid(30);
        chat.setUser1(user1);
        chat.setUser2(user2);

        when(chatRepo.save(any(Chat.class))).thenAnswer(invocation -> invocation.getArgument(0));

        chatController.createChat(chat);

        ArgumentCaptor<Chat> captor = ArgumentCaptor.forClass(Chat.class);
        verify(chatRepo).save(captor.capture());

        Chat captured = captor.getValue();
        assertThat(captured.getChatid()).isEqualTo(30);
        assertThat(captured.getUser1().getSupabaseUserId()).isEqualTo("supabase-5");
        assertThat(captured.getUser2().getSupabaseUserId()).isEqualTo("supabase-6");
    }
}