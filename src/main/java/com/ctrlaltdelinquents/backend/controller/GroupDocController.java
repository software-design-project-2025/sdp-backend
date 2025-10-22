package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.GroupDoc;
import com.ctrlaltdelinquents.backend.repo.GroupDocRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/groupdocs") // Base URL for all endpoints in this controller
public class GroupDocController {

    @Autowired
    private GroupDocRepo groupDocRepo;

    /**
     * Creates a new chat document record.
     * This endpoint is called after a document has been uploaded to Azure and you have its URL.
     * @param newGroupDoc A JSON object in the request body with senderID, chatID, and the doc URL.
     * @return The created ChatDoc object.
     */
    @PostMapping
    public ResponseEntity<GroupDoc> createGroupDoc(@RequestBody GroupDoc newGroupDoc) {
        try {
            // Set the current timestamp before saving
            newGroupDoc.setSent_datetime(LocalDateTime.now());
            GroupDoc savedDoc = groupDocRepo.save(newGroupDoc);
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
    public ResponseEntity<List<GroupDoc>> getAllGroupDocs() {
        try {
            List<GroupDoc> documents = groupDocRepo.findAll();
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
    public ResponseEntity<GroupDoc> getGroupDocById(@PathVariable("id") Integer id) {
        Optional<GroupDoc> groupDocData = groupDocRepo.findById(id);

        // The .map() function here provides a clean way to handle both cases
        return groupDocData.map(groupDoc -> new ResponseEntity<>(groupDoc, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * (Optional but Recommended) Retrieves all documents for a specific chat.
     * @param groupId The ID of the chat.
     * @return A list of ChatDoc objects for the given chat.
     */
    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<GroupDoc>> getDocsByGroupId(@PathVariable("groupId") Integer groupId) {
        try {
            List<GroupDoc> documents = groupDocRepo.findByGroupID(groupId);
            if (documents.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(documents, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}