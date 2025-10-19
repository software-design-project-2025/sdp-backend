package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "\"group\"")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "groupid")
    private Integer groupid;

    @Column(name = "title")
    private String title;

    @Column(name = "goal")
    private String goal;

    @Column(name = "active")
    private boolean active = true;

    @Column(name = "creatorid")
    private String creatorid;
}
