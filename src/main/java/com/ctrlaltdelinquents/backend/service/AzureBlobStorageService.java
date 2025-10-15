package com.ctrlaltdelinquents.backend.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class AzureBlobStorageService {

    // The starter library automatically creates this bean for us
    @Autowired
    private BlobContainerClient blobContainerClient;

    /**
     * Uploads a file to Azure Blob Storage.
     * @param file The file to upload.
     * @param userId The ID of the user to associate with the file.
     * @return The public URL of the uploaded file.
     * @throws IOException If an I/O error occurs.
     */
    public String upload(MultipartFile file, String userId) throws IOException {
        // 1. Generate a unique filename to avoid overwriting existing files
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String uniqueFileName = "user-" + userId + "-" + UUID.randomUUID() + fileExtension;

        // 2. Get a reference to a blob in the container
        BlobClient blobClient = blobContainerClient.getBlobClient(uniqueFileName);

        // 3. Upload the file's data to the blob
        // This is the line that performs the actual upload to Azure
        blobClient.upload(file.getInputStream(), file.getSize(), true);

        // 4. Return the public URL of the blob
        return blobClient.getBlobUrl();
    }

    private String getFileExtension(String fileName) {
        if (fileName != null && fileName.lastIndexOf(".") != -1) {
            return fileName.substring(fileName.lastIndexOf("."));
        }
        return "";
    }
}