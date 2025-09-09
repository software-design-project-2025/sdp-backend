package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "\"user\"")  // Keep team's table name
public class User {

    @Id
    private String userid;

    @Column
    private String role;

    @JoinColumn(name = "degreeid")
    private int degreeid;

    @Column
    private int yearofstudy;

    @Column
    private String bio;

    @Column
    private String status;

    @Column
    private String profile_picture;
}
