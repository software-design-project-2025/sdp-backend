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
    private int userid;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "role")
    private String role;

    @Column(name = "is_active")
    private boolean is_active;

    @Column(name = "bio")
    private String bio;

    @Column(name = "profile_picture")  
    private String profile_picture;
}
