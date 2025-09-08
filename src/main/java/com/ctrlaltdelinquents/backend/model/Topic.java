package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "topic")
public class Topic {

    @Id
    @Column(name = "topicid")
    private int topicId;

    @Column(name = "title")
    private String topicName;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;


    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "course_code"),
            @JoinColumn(name = "userid")
    })
    private UserCourse userCourse;
}
