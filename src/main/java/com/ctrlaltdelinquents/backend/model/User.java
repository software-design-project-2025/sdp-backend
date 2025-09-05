package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "\"user\"")  
public class User {

    @Id
    @Column(name = "userid")  
    private String userid;

    @Column(name = "role")
    private String role;

    @Column(name = "degreeid")
    private String degreeid;

    @Column(name = "yearofstudy")
    private int yearofstudy;

    @Column(name = "bio")
    private String bio;

    @Column(name = "status")
    private String status;

    @Column(name = "profile_picture")  
    private String profile_picture;
}
