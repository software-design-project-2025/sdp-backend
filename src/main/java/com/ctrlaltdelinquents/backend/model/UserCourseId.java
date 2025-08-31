package com.ctrlaltdelinquents.backend.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode // Important for composite keys
public class UserCourseId implements Serializable {
    private int user; // Corresponds to the type of User's primary key
    private String courseCode; // Corresponds to the type of Module's primary key
}