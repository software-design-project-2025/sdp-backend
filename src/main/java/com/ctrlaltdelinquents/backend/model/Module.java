package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "module")
public class Module {

    @Id
    @Column(name = "course_code")
    private String courseCode;

    @Column(name = "course_name")
    private String course_name;

    @Column(name = "facultyid")
    private int facultyid;

//    @ManyToOne
//    @JoinColumn(name = "facultyid")
//    private Faculty facultyid;
}
