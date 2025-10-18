package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "group_members")
@IdClass(GroupMemberId.class) // Specify the composite key class
public class GroupMembers {

    @Id
    @Column(name = "groupid")
    private Integer groupid;

    @Id
    @Column(name = "userid")
    private String userid;
}
