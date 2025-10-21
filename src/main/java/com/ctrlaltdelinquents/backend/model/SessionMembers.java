package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@IdClass(SessionMembersId.class) // Use the IdClass
@Table(name = "session_members")
public class SessionMembers {

    @Id
    @Column(name = "sessionid")
    private Integer sessionid; // This is the ID (Integer)

    @Id
    @Column(name = "userid")
    private String userid; // This is the ID (String)

    // --- Mappings for retrieving the actual objects ---
    // These are NOT part of the ID

    @ManyToOne
    @JoinColumn(name = "sessionid", referencedColumnName = "sessionid", insertable = false, updatable = false)
    private Session session;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid", insertable = false, updatable = false)
    private User user;

    // --- Constructors ---

    public SessionMembers() {}

    // This constructor is used by your SessionMembersService
    public SessionMembers(Integer sessionid, String userid) {
        this.sessionid = sessionid;
        this.userid = userid;
    }
}