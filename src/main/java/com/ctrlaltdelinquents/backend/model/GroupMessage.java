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
    @Column(name = "messageid")
    private int messageId;

    @ManyToOne
    @JoinColumn(name = "groupid", referencedColumnName = "groupid")
    private Group group;

    @Column(name = "sent_date_time")
    private LocalDateTime sentDateTime;

    @ManyToOne
    @JoinColumn(name = "senderid", referencedColumnName = "userid")
    private User sender;

    @Column(name = "message")
    private String message;
}
