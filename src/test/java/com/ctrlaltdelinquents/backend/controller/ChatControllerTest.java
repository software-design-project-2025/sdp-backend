// package com.ctrlaltdelinquents.backend.controller;

// import com.ctrlaltdelinquents.backend.model.Chat;
// import com.ctrlaltdelinquents.backend.model.User;
// import com.ctrlaltdelinquents.backend.repo.ChatRepo;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Disabled;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.mockito.ArgumentCaptor;

// import java.util.Optional;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.mockito.Mockito.*;
// @Disabled
// class ChatControllerTest { 

//     private ChatRepo chatRepo;
//     private ChatController chatController;

//     @BeforeEach
//     void setUp() {
//         chatRepo = mock(ChatRepo.class);
//         chatController = new ChatController(chatRepo);
//     }

//     @Test
//     @DisplayName("Should return chat when chat with given id exists")
//     void getChatsByUser_returnsChat_whenChatExists() {
//         User user1 = new User();
//         //user1.setUserid(1);
//         User user2 = new User();
//         //user2.setUserid(2);

//         Chat chat = new Chat();
//         chat.setChatid(10);
//         chat.setUser1(user1);
//         chat.setUser2(user2);

//         when(chatRepo.findById(10)).thenReturn(Optional.of(chat));

//         /*Optional<Chat> result = chatController.getChatsByUser(10);

//         assertThat(result).isPresent();
//         assertThat(result.get().getChatid()).isEqualTo(10);
//         assertThat(result.get().getUser1().getUserid()).isEqualTo(1);
//         assertThat(result.get().getUser2().getUserid()).isEqualTo(2);
//         verify(chatRepo, times(1)).findById(10);*/
//     }

//     @Test
//     @DisplayName("Should return empty when chat with given id does not exist")
//     void getChatsByUser_returnsEmpty_whenChatDoesNotExist() {
//         when(chatRepo.findById(99)).thenReturn(Optional.empty());

//         //Optional<Chat> result = chatController.getChatsByUser(99);

//         //assertThat(result).isEmpty();
//         //verify(chatRepo, times(1)).findById(99);
//     }

//     @Test
//     @DisplayName("Should save and return chat when creating new chat")
//     void createChat_savesAndReturnsChat() {
//         User user1 = new User();
//         //user1.setUserid(1);
//         User user2 = new User();
//         //user2.setUserid(2);

//         Chat chat = new Chat();
//         chat.setChatid(20);
//         chat.setUser1(user1);
//         chat.setUser2(user2);

//         when(chatRepo.save(chat)).thenReturn(chat);

//         Chat saved = chatController.createChat(chat);

//         assertThat(saved.getChatid()).isEqualTo(20);
//         assertThat(saved.getUser1().getUserid()).isEqualTo(1);
//         assertThat(saved.getUser2().getUserid()).isEqualTo(2);
//         verify(chatRepo, times(1)).save(chat);
//     }

//     @Test
//     @DisplayName("Should pass correct entity to repository when saving chat")
//     void createChat_passesCorrectEntityToRepo() {
//         User user1 = new User();
//         //user1.setUserid(5);
//         User user2 = new User();
//         //user2.setUserid(6);

//         Chat chat = new Chat();
//         chat.setChatid(30);
//         chat.setUser1(user1);
//         chat.setUser2(user2);

//         when(chatRepo.save(any(Chat.class))).thenAnswer(invocation -> invocation.getArgument(0));

//         chatController.createChat(chat);

//         ArgumentCaptor<Chat> captor = ArgumentCaptor.forClass(Chat.class);
//         verify(chatRepo).save(captor.capture());

//         Chat captured = captor.getValue();
//         assertThat(captured.getChatid()).isEqualTo(30);
//         assertThat(captured.getUser1().getUserid()).isEqualTo(5);
//         assertThat(captured.getUser2().getUserid()).isEqualTo(6);
//     }
// }
