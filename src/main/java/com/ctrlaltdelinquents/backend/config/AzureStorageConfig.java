package com.ctrlaltdelinquents.backend.config; // Use your project's package name

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AzureStorageConfig {

    @Value("${azure.storage.blob.connection-string}")
    private String connectionString;

    @Value("${azure.storage.container.name}")
    private String containerName;

    @Bean
    public BlobContainerClient blobContainerClient() {
        // This code explicitly builds the client bean
        return new BlobContainerClientBuilder()
                .connectionString(connectionString)
                .containerName(containerName)
                .buildClient();
    }
}