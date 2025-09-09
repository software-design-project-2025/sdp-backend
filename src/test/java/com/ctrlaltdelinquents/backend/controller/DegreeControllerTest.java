package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.Degree;
import com.ctrlaltdelinquents.backend.repo.DegreeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class DegreeControllerTest {

    private DegreeRepo degreeRepo;
    private DegreeController degreeController;

    @BeforeEach
    void setUp() {
        degreeRepo = mock(DegreeRepo.class);
        degreeController = new DegreeController(degreeRepo);
    }

    @Test
    @DisplayName("Should return all degrees when repository contains entries")
    void getAllDegree_returnsAllDegrees() {
        Degree degree1 = new Degree();
        degree1.setDegreeid(1);
        degree1.setDegree_name("Computer Science");
        degree1.setDegree_type("BSc");
        degree1.setFacultyid(10);

        Degree degree2 = new Degree();
        degree2.setDegreeid(2);
        degree2.setDegree_name("Mechanical Engineering");
        degree2.setDegree_type("BEng");
        degree2.setFacultyid(20);

        when(degreeRepo.findAll()).thenReturn(List.of(degree1, degree2));

        List<Degree> result = degreeController.getAllDegree();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getDegree_name()).isEqualTo("Computer Science");
        assertThat(result.get(1).getDegree_type()).isEqualTo("BEng");
        verify(degreeRepo, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return empty list when repository has no degrees")
    void getAllDegree_returnsEmptyList_whenNoDegreesExist() {
        when(degreeRepo.findAll()).thenReturn(List.of());

        List<Degree> result = degreeController.getAllDegree();

        assertThat(result).isEmpty();
        verify(degreeRepo, times(1)).findAll();
    }
}
