package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "faculty")
public class Faculty {

    @Id
    @Column(name = "facultyid")
    private int facultyid;

    @Column(name = "faculty_name")
    private String faculty_name;

}