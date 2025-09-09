/*package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.Faculty;
import com.ctrlaltdelinquents.backend.repo.FacultyRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class FacultyControllerTest {

    private FacultyRepo facultyRepo;
    private FacultyController facultyController;

    @BeforeEach
    void setUp() {
        facultyRepo = mock(FacultyRepo.class);
        facultyController = new FacultyController(facultyRepo);
    }

    @Test
    @DisplayName("Should return all faculties when repository contains entries")
    void getAllFaculty_returnsAllFaculties() {
        Faculty faculty1 = new Faculty();
        faculty1.setFacultyid(1);
        faculty1.setFaculty_name("Engineering");

        Faculty faculty2 = new Faculty();
        faculty2.setFacultyid(2);
        faculty2.setFaculty_name("Science");

        when(facultyRepo.findAll()).thenReturn(List.of(faculty1, faculty2));

        List<Faculty> result = facultyController.getAllFaculty();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getFaculty_name()).isEqualTo("Engineering");
        assertThat(result.get(1).getFaculty_name()).isEqualTo("Science");
        verify(facultyRepo, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when repository has no faculties")
    void getAllFaculty_returnsEmptyList_whenNoFacultiesExist() {
        when(facultyRepo.findAll()).thenReturn(List.of());

        List<Faculty> result = facultyController.getAllFaculty();

        assertThat(result).isEmpty();
        verify(facultyRepo, times(1)).findAll();
    }

    @Test
    @DisplayName("Should call repository findAll exactly once")
    void getAllFaculty_callsRepositoryOnce() {
        when(facultyRepo.findAll()).thenReturn(List.of());
        facultyController.getAllFaculty();
        verify(facultyRepo, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return correct faculty ids and names")
    void getAllFaculty_returnsCorrectFacultyData() {
        Faculty faculty = new Faculty();
        faculty.setFacultyid(5);
        faculty.setFaculty_name("Arts");

        when(facultyRepo.findAll()).thenReturn(List.of(faculty));

        List<Faculty> result = facultyController.getAllFaculty();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFacultyid()).isEqualTo(5);
        assertThat(result.get(0).getFaculty_name()).isEqualTo("Arts");
    }

    @Test
    @DisplayName("Should pass correct entity type from repository")
    void getAllFaculty_returnsEntityOfCorrectType() {
        Faculty faculty = new Faculty();
        faculty.setFacultyid(10);
        faculty.setFaculty_name("Business");

        when(facultyRepo.findAll()).thenReturn(List.of(faculty));

        List<Faculty> result = facultyController.getAllFaculty();

        assertThat(result).allMatch(Objects::nonNull);
        assertThat(result.get(0).getFaculty_name()).isEqualTo("Business");
    }
}*/
