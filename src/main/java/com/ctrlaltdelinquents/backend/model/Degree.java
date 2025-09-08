package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "degree")
public class Degree {

    @Id
    @Column(name = "degreeid")
    private int degreeid;

    @Column(name = "degree_name")
    private String degree_name;

    @Column(name = "degree_type")
    private String degree_type;

    @Column(name = "facultyid")
    private int facultyid;

//    @ManyToOne
//    @JoinColumn(name = "facultyid", referencedColumnName = "facultyid")
//    private Faculty faculty;
}
