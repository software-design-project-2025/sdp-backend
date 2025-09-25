package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "chat_doc")
public class ChatDoc {

    @Id
    @Column(name = "docid")
    private int docid;

    @JoinColumn(name = "chatid")
    private int chatid;

    @JoinColumn(name = "senderid")
    private String senderid;

    @Column(name = "sent_datetime")
    private LocalDateTime sent_datetime;

    @Column(name = "doc")
    private String doc;
}
