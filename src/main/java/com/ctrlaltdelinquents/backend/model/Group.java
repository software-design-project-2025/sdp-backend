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
    @Column(name = "groupid")
    private int groupid;

    @Column(name = "title")
    private String title;

    @Column(name = "goal")
    private String goal;

    @Column(name = "active")
    private boolean active;

    @JoinColumn(name = "creatorid")
    private String creatorid;
}
