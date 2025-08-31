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
    @Column(name = "sessionid")
    private int sessionId;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "creatorid")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "groupid")
    private Group group;
}
