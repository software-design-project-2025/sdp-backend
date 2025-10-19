package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "original_filename", nullable = false)
    private String originalFilename;

    @Column(name = "blob_name", nullable = false, unique = true)
    private String blobName;

    private String contentType;

    @Column(name = "uploaded_at")
    private Instant uploadedAt;
}