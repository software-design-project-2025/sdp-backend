package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
    @Column(name = "messageid")
    private Integer messageid;

    @JoinColumn(name = "chatid")
    private Integer chatid;

    @JoinColumn(name = "senderid")
    private String senderid;

    @Column(name = "sent_datetime")
    private LocalDateTime sent_datetime;

    @Column(name = "message")
    private String message;

    @Column(name = "read_status")
    private boolean read_status;
}
