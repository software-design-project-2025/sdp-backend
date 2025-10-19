package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.Document;
import com.ctrlaltdelinquents.backend.service.AzureBlobStorageService;
import com.ctrlaltdelinquents.backend.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/documents")
public class DocumentController {

    @Autowired
    private AzureBlobStorageService storageService;

    @Autowired
    private DocumentService documentService;

    /**
     * Endpoint to upload a document.
     * The file is sent as multipart/form-data.
     * @param file The document file to upload.
     * @param userId The ID of the user uploading the file.
     * @return A ResponseEntity containing the metadata of the saved document.
     */
    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocument(@RequestParam("file") MultipartFile file, @RequestParam("userId") String userId) {
        try {
            // Step 1: Upload the file to Azure and get the unique blob name
            String blobName = storageService.uploadDocument(file, userId);

            // Step 2: Save the document's metadata to the database
            Document doc = documentService.saveDocumentMetadata(userId, file.getOriginalFilename(), blobName, file.getContentType());

            // Step 3: Return the saved document metadata
            return ResponseEntity.ok(doc);
        } catch (IOException e) {
            // Log the error for debugging: e.g., log.error("File upload failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload document.");
        }
    }

    /**
     * Endpoint to get a secure, short-lived download URL for a document.
     * @param id The ID of the document record in the database.
     * @return A ResponseEntity containing a JSON object with the secure download URL.
     */
    @GetMapping("/{id}/download-url")
    public ResponseEntity<?> getDownloadUrl(@PathVariable Long id) {
        // Step 1: Find the document metadata from the database
        Optional<Document> docOptional = documentService.findById(id); // Assuming findById returns an Optional<Document>

        if (docOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Document doc = docOptional.get();

        // Step 2: Generate the secure SAS URL for the blob
        String sasUrl = storageService.generateSasUrl(doc.getBlobName());

        // Step 3: Return the URL in a JSON object
        return ResponseEntity.ok(Map.of("downloadUrl", sasUrl));
    }
}