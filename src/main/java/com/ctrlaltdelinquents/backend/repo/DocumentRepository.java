package com.ctrlaltdelinquents.backend.repo;

import com.ctrlaltdelinquents.backend.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {}
