package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "chat_doc") // Matches your table name
public class ChatDoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments the primary key
    @Column(name = "docid")
    private Integer cdID;

    @Column(name = "sent_datetime")
    private LocalDateTime sentDateTime;

    @Column(name = "senderid")
    private String senderID;

    @Column(name = "chatid")
    private Integer chatID;

    @Column(name = "doc")
    private String doc; // This will store the URL to the document in Azure Blob Storage
}