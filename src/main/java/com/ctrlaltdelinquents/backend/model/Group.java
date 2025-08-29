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

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "goal", columnDefinition = "TEXT")
    private String goal;

    @Column(name = "active")
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "creatorid", referencedColumnName = "userid")
    private User creator;
}
