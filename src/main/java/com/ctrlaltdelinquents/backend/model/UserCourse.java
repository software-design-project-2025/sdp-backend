package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "user_course")
public class UserCourse {

    @Id
    @Column(name = "user_courseid")
    private int userCourseId;

    @ManyToOne
    @JoinColumn(name = "userid")
    private User user;

    @Column(name = "course_code")
    private String courseCode;
}
