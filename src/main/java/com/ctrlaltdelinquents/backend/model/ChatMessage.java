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
    @Column(name = "messageid")
    private int messageid;

    @ManyToOne
    @JoinColumn(name = "chatid", referencedColumnName = "chatid")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "senderid", referencedColumnName = "userid")
    private User sender;

    @Column(name = "sent_date_time")
    private LocalDateTime sentDateTime;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;
}
