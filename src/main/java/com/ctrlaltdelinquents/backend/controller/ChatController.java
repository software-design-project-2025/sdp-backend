package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.Chat;
import com.ctrlaltdelinquents.backend.repo.ChatRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RestController
@RequestMapping("/api/auth")
public class ChatController {

    @Autowired
    private final ChatRepo chatRepo;

    public ChatController(ChatRepo chatRepo) {
        this.chatRepo = chatRepo;
    }

    // GET all chats
    @GetMapping("/chat")  
    @ResponseBody
    public Optional<Chat> getChatsByUser(@RequestParam int userid) {

        return chatRepo.findById(userid);
    }

    // POST a new user
    @PostMapping("/chat")
    public Chat createChat(@RequestBody Chat chat) {
        return chatRepo.save(chat);
    }
}
    

