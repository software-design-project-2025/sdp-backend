package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "chatdoc")
public class ChatDoc {

    @Id
    @Column(name = "docid")
    private int docid;

    @ManyToOne
    @JoinColumn(name = "chatid", referencedColumnName = "chatid")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "senderid", referencedColumnName = "userid")
    private User sender;

    @Column(name = "sent_date_time")
    private LocalDateTime sentDateTime;

    @Column(name = "doc", length = 255)
    private String doc;
}
