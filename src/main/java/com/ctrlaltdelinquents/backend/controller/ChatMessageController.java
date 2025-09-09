// package com.ctrlaltdelinquents.backend.controller;

// import com.ctrlaltdelinquents.backend.model.ChatMessage;
// import com.ctrlaltdelinquents.backend.repo.ChatMessageRepo;
// import org.springframework.web.bind.annotation.RequestBody;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.web.bind.annotation.RestController;

// import java.util.List;


// @RestController
// @RequestMapping("/api/chatMessage")
// public class ChatMessageController {

//    @Autowired
//    private final ChatMessageRepo chatMessageRepo;

//    public ChatMessageController(ChatMessageRepo chatMessageRepo) {
//        this.chatMessageRepo = chatMessageRepo;
//    }

//    // GET all chats
//    @GetMapping("/getMessage")
//    @ResponseBody
//    public List<ChatMessage> getMessageByChat(@RequestParam int chatid) {
//        return chatMessageRepo.findByChat(chatid);
//    }

//    @PostMapping("/sendMessage")
//    public ChatMessage createMessage(@RequestBody ChatMessage message) {
//        return chatMessageRepo.save(message);
//    }

// }


