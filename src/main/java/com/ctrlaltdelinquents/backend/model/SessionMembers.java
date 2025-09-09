package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@IdClass(SessionMembersId.class)
@Table(name = "session_members")
public class SessionMembers {

    @Id
    @ManyToOne
    @JoinColumn(name = "sessionid")
    private Session sessionid;

    @Id
    @ManyToOne
    @JoinColumn(name = "userid")
    private User userid;
}
