package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.Chat;
import com.ctrlaltdelinquents.backend.model.User;
import com.ctrlaltdelinquents.backend.repo.ChatRepo;
import com.ctrlaltdelinquents.backend.repo.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//import java.util.Optional;
import java.util.List;


@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private final ChatRepo chatRepo;

    private final UserRepository userRepository;

    public ChatController(ChatRepo chatRepo, UserRepository userRepository) {
        this.chatRepo = chatRepo;
        this.userRepository = userRepository;
    }

    // GET all chats
    @GetMapping("/getChat")  
    @ResponseBody
    public List<Chat> getChatsByUser(@RequestParam String userid) {
        return chatRepo.findByUser(userid);
    }

    // POST a new user
    @PostMapping("/createChat")
    public Chat createChat(@RequestBody Chat chat) {
        // Get managed User entities from database
        User managedUser1 = userRepository.findById(chat.getUser1().getUserid())
            .orElseThrow(() -> new RuntimeException("User1 not found"));
        User managedUser2 = userRepository.findById(chat.getUser2().getUserid())
            .orElseThrow(() -> new RuntimeException("User2 not found"));

        // Check if chat exists
        List<Chat> existingChats = chatRepo.findChat(
            managedUser1.getUserid(), 
            managedUser2.getUserid()
        );

        if (!existingChats.isEmpty()) {
            return existingChats.get(0);
        }

        // Create NEW chat with MANAGED entities
        Chat newChat = new Chat();
        newChat.setUser1(managedUser1);  // These are from database
        newChat.setUser2(managedUser2);  // Not transient!

        return chatRepo.save(newChat);
    }
}
