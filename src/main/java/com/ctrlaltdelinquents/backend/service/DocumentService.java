package com.ctrlaltdelinquents.backend.service;

import com.ctrlaltdelinquents.backend.model.Document;
import com.ctrlaltdelinquents.backend.repo.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    public Document saveDocumentMetadata(String userId,
                                         String originalFilename,
                                         String blobName,
                                         String contentType) {
        Document doc = new Document();
        doc.setUserId(userId);
        doc.setOriginalFilename(originalFilename);
        doc.setBlobName(blobName);
        doc.setContentType(contentType);
        doc.setUploadedAt(Instant.now());
        return documentRepository.save(doc);
    }

    public Optional<Document> findById(Long id) {
        return documentRepository.findById(id);
    }
}
