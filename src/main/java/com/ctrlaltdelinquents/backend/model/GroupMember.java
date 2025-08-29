package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "group_member")
public class GroupMember {

    @Id
    @Column(name = "group_memberid")
    private int groupMemberId;

    @ManyToOne
    @JoinColumn(name = "groupid", referencedColumnName = "groupid")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid")
    private User user;
}
