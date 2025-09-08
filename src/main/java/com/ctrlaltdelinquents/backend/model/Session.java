package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "session")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sessionid")
    private Integer sessionId;

    @Column(name = "title")
    private String title;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "status")
    private String status;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    // This should be a simple String reference, not a ManyToOne relationship
    // since creatorid is just a TEXT field in the database
    @Column(name = "creatorid")
    private String creatorId;

    // This should also be a simple Integer reference
    @Column(name = "groupid")
    private Integer groupId;
}