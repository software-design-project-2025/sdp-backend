package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.ChatDoc;
import com.ctrlaltdelinquents.backend.repo.ChatDocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chatdocs") // Base URL for all endpoints in this controller
public class ChatDocController {

    @Autowired
    private ChatDocRepository chatDocRepository;

    /**
     * Creates a new chat document record.
     * This endpoint is called after a document has been uploaded to Azure and you have its URL.
     * @param newChatDoc A JSON object in the request body with senderID, chatID, and the doc URL.
     * @return The created ChatDoc object.
     */
    @PostMapping
    public ResponseEntity<ChatDoc> createChatDoc(@RequestBody ChatDoc newChatDoc) {
        try {
            // Set the current timestamp before saving
            newChatDoc.setSentDateTime(LocalDateTime.now());
            ChatDoc savedDoc = chatDocRepository.save(newChatDoc);
            return new ResponseEntity<>(savedDoc, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves all chat documents from the database.
     * @return A list of all ChatDoc objects.
     */
    @GetMapping("/all")
    public ResponseEntity<List<ChatDoc>> getAllChatDocs() {
        try {
            List<ChatDoc> documents = chatDocRepository.findAll();
            if (documents.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(documents, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves a single chat document by its unique ID.
     * @param id The primary key (cdID) of the chat document.
     * @return The ChatDoc object if found, otherwise a 404 Not Found error.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ChatDoc> getChatDocById(@PathVariable("id") Integer id) {
        Optional<ChatDoc> chatDocData = chatDocRepository.findById(id);

        // The .map() function here provides a clean way to handle both cases
        return chatDocData.map(chatDoc -> new ResponseEntity<>(chatDoc, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * (Optional but Recommended) Retrieves all documents for a specific chat.
     * @param chatId The ID of the chat.
     * @return A list of ChatDoc objects for the given chat.
     */
    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<ChatDoc>> getDocsByChatId(@PathVariable("chatId") Integer chatId) {
        try {
            List<ChatDoc> documents = chatDocRepository.findByChatID(chatId);
            if (documents.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(documents, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}