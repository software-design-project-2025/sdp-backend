package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.ChatMessage;
import com.ctrlaltdelinquents.backend.repo.ChatMessageRepo;
import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import org.mockito.ArgumentCaptor;

class ChatMessageControllerTest { 

    private ChatMessageRepo chatMessageRepo;
    private ChatMessageController chatMessageController;

    @BeforeEach
    void setUp() {
        chatMessageRepo = mock(ChatMessageRepo.class);
        chatMessageController = new ChatMessageController(chatMessageRepo);
    }

    @Test
    @DisplayName("Should return ChatMessage when ChatMessage with given id exists")
    void getMessageByChat_returnsMessage_whenMessageExists() {

        ChatMessage message = new ChatMessage();
        message.setMessageid(1);
        message.setChatid(1);
        message.setSenderid("1");
        message.setSent_datetime(LocalDateTime.parse("2025-09-20T19:30:45"));
        message.setMessage("Hi");

        when(chatMessageRepo.findByChat(1)).thenReturn(List.of(message));

        List<ChatMessage> result = chatMessageController.getMessageByChat(1);

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getChatid()).isEqualTo(1);
        assertThat(result.get(0).getMessageid()).isEqualTo(1);
        assertThat(result.get(0).getSenderid()).isEqualTo("1");
        assertThat(result.get(0).getSent_datetime()).isEqualTo(LocalDateTime.parse("2025-09-20T19:30:45"));
        assertThat(result.get(0).getMessage()).isEqualTo("Hi");
    
        verify(chatMessageRepo, times(1)).findByChat(1);
    }

 
    @Test
    @DisplayName("Should return empty when ChatMessage with given chat id does not exist")
    void getMessageByChat_returnsEmpty_whenMessageDoesNotExist() {
        when(chatMessageRepo.findByChat(99)).thenReturn(List.of());

        List<ChatMessage> result = chatMessageController.getMessageByChat(99);

        assertThat(result).isEmpty();
        verify(chatMessageRepo, times(1)).findByChat(99);
    }

 
    @Test
    @DisplayName("Should save and return ChatMessage when creating new ChatMessage")
    void createChatMessage_savesAndReturnsChatMessage() {
        ChatMessage message = new ChatMessage();
        message.setMessageid(1);
        message.setChatid(1);
        message.setSenderid("1");
        message.setSent_datetime(LocalDateTime.parse("2025-09-20T19:30:45"));
        message.setMessage("Hi");

        when(chatMessageRepo.save(message)).thenReturn(message);

        ChatMessage saved = chatMessageController.createMessage(message);

        assertThat(saved.getChatid()).isEqualTo(1);
        assertThat(saved.getMessageid()).isEqualTo(1);
        assertThat(saved.getSenderid()).isEqualTo("1");
        assertThat(saved.getSent_datetime()).isEqualTo(LocalDateTime.parse("2025-09-20T19:30:45"));
        assertThat(saved.getMessage()).isEqualTo("Hi");
        verify(chatMessageRepo, times(1)).save(message);
    }


    @Test
    @DisplayName("Should pass correct entity to repository when saving chat")
    void createChatMessage_passesCorrectEntityToRepo() {
        ChatMessage message = new ChatMessage();
        message.setMessageid(1);
        message.setChatid(1);
        message.setSenderid("1");
        message.setSent_datetime(LocalDateTime.parse("2025-09-20T19:30:45"));
        message.setMessage("Hi");


        when(chatMessageRepo.save(any(ChatMessage.class))).thenAnswer(invocation -> invocation.getArgument(0));

        chatMessageController.createMessage(message);

        ArgumentCaptor<ChatMessage> captor = ArgumentCaptor.forClass(ChatMessage.class);
        verify(chatMessageRepo).save(captor.capture());

        ChatMessage captured = captor.getValue();
        assertThat(captured.getChatid()).isEqualTo(1);
        assertThat(captured.getMessageid()).isEqualTo(1);
        assertThat(captured.getSenderid()).isEqualTo("1");
        assertThat(captured.getSent_datetime()).isEqualTo(LocalDateTime.parse("2025-09-20T19:30:45"));
        assertThat(captured.getMessage()).isEqualTo("Hi");
     
    }
}

