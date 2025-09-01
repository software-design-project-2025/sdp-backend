package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "user_course")
@IdClass(UserCourseId.class)
public class UserCourse {

    @Id
    @Column(name = "userid")
    private int userid;

    @Id
    @Column(name = "course_code")
    private String courseCode;
}