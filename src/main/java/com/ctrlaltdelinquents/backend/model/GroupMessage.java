package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "group_message")
public class GroupMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increments the primary key
    @Column(name = "messageid")
    private int messageid;

    @JoinColumn(name = "groupid")
    private Integer groupid;

    @Column(name = "sent_datetime")
    private LocalDateTime sent_datetime;

    @JoinColumn(name = "senderid")
    private String senderid;

    @Column(name = "message")
    private String message;
}
