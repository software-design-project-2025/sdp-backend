package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "group_doc")
public class GroupDoc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments the primary key
    @Column(name = "docid")
    private int docid;

    @JoinColumn(name = "groupid")
    private int groupid;

    @Column(name = "sent_datetime")
    private LocalDateTime sent_datetime;

    @JoinColumn(name = "senderid")
    private String senderid;

    @Column(name = "doc")
    private String doc;
}
