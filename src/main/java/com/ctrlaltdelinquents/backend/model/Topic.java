package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "topic")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer topicid;

    @JoinColumn(name = "userid")
    private String userid;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private LocalDateTime start_date;

    @Column
    private LocalDateTime end_date;

    @Column
    private String status;

    @JoinColumn(name = "course_code")
    private String course_code;

    @Column
    private BigDecimal hours;
}
