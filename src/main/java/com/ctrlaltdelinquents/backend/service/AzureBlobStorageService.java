package com.ctrlaltdelinquents.backend.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class AzureBlobStorageService {

    // Spring will now inject the bean we created in AzureStorageConfig.java
    @Autowired
    private BlobServiceClient blobServiceClient;

    // Read the container names from application.properties
    @Value("${azure.storage.container.name}")
    private String profilePicturesContainer;

    @Value("${azure.storage.container.name.documents}")
    private String documentsContainer;

    // --- Profile Picture Method ---
    public String upload(MultipartFile file, String userId) throws IOException {
        String sanitizedUserId = userId.replace("|", "-");
        String uniqueFileName = "user-" + sanitizedUserId + "-" + UUID.randomUUID() + getFileExtension(file.getOriginalFilename());

        // Use the master client to get the specific container client for profile pictures
        BlobClient blobClient = blobServiceClient.getBlobContainerClient(profilePicturesContainer)
                .getBlobClient(uniqueFileName);

        blobClient.upload(file.getInputStream(), file.getSize(), true);
        return blobClient.getBlobUrl();
    }

    // --- Document Methods ---
    public String uploadDocument(MultipartFile file, String userId) throws IOException {
        String sanitizedUserId = userId.replace("|", "-");
        String uniqueBlobName = "doc-" + sanitizedUserId + "-" + UUID.randomUUID() + getFileExtension(file.getOriginalFilename());

        // Use the same master client to get the container client for documents
        BlobClient blobClient = blobServiceClient.getBlobContainerClient(documentsContainer)
                .getBlobClient(uniqueBlobName);
        blobClient.upload(file.getInputStream(), file.getSize(), true);
        return uniqueBlobName;
    }

    public String generateSasUrl(String blobName) {
        BlobClient blobClient = blobServiceClient.getBlobContainerClient(documentsContainer)
                .getBlobClient(blobName);

        BlobSasPermission sasPermission = new BlobSasPermission().setReadPermission(true);
        OffsetDateTime expiryTime = OffsetDateTime.now().plusMinutes(5);
        BlobServiceSasSignatureValues sasValues = new BlobServiceSasSignatureValues(expiryTime, sasPermission);

        return String.format("%s?%s", blobClient.getBlobUrl(), blobClient.generateSas(sasValues));
    }

    private String getFileExtension(String fileName) {
        if (fileName != null && fileName.lastIndexOf(".") != -1) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return "";
    }
}