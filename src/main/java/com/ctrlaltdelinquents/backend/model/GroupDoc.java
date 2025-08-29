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
    @Column(name = "docid")
    private int docid;

    @ManyToOne
    @JoinColumn(name = "groupid", referencedColumnName = "groupid")
    private Group group;

    @Column(name = "sent_date_time")
    private LocalDateTime sentDateTime;

    @ManyToOne
    @JoinColumn(name = "senderid", referencedColumnName = "userid")
    private User sender;

    @Column(name = "doc", length = 255)
    private String doc;
}
