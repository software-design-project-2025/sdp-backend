package com.ctrlaltdelinquents.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @Column(name = "degreename")
    private String degreename;

    @Column(name = "degreetype")
    private String degreetype;

    @ManyToOne
    @JoinColumn(name = "facultyid", referencedColumnName = "facultyid")
    private Faculty faculty;
}
