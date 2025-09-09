package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.Chat;
import com.ctrlaltdelinquents.backend.repo.ChatRepo;

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

   public ChatController(ChatRepo chatRepo) {
       this.chatRepo = chatRepo;
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
       return chatRepo.save(chat);
   }
}
