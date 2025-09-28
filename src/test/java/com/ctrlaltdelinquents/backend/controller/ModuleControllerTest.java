package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.controller.ModuleController;
import com.ctrlaltdelinquents.backend.model.Module;
import com.ctrlaltdelinquents.backend.repo.ModuleRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ModuleControllerTest {

    private final ModuleRepo moduleRepo = mock(ModuleRepo.class);
    private final ModuleController moduleController = new ModuleController(moduleRepo);

    @Test
    @DisplayName("Should return a list with one module")
    void getAllModule_returnsSingleModule() {
        Module module = new Module();
        module.setCourseCode("CS101");
        module.setCourseName("Software Engineering");
        module.setFacultyId(1);

        when(moduleRepo.findAll()).thenReturn(List.of(module));

        List<Module> result = moduleController.getAllModule();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCourseCode()).isEqualTo("CS101");
        assertThat(result.get(0).getCourseName()).isEqualTo("Software Engineering");
        assertThat(result.get(0).getFacultyId()).isEqualTo(1);
    }

    @Test
    @DisplayName("Should return a list with multiple modules")
    void getAllModule_returnsMultipleModules() {
        Module m1 = new Module();
        m1.setCourseCode("CS101");
        m1.setCourseName("Software Engineering");
        m1.setFacultyId(1);

        Module m2 = new Module();
        m2.setCourseCode("CS102");
        m2.setCourseName("Data Structures");
        m2.setFacultyId(1);

        when(moduleRepo.findAll()).thenReturn(List.of(m1, m2));

        List<Module> result = moduleController.getAllModule();

        assertThat(result).hasSize(2);
        assertThat(result).extracting(Module::getCourseCode)
                .containsExactly("CS101", "CS102");
    }

    @Test
    @DisplayName("Should return an empty list when no modules exist")
    void getAllModule_returnsEmptyList() {
        when(moduleRepo.findAll()).thenReturn(List.of());

        List<Module> result = moduleController.getAllModule();

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("All returned modules should be non-null")
    void getAllModule_allModulesAreNonNull() {
        Module m1 = new Module();
        m1.setCourseCode("CS201");
        m1.setCourseName("Algorithms");
        m1.setFacultyId(2);

        Module m2 = new Module();
        m2.setCourseCode("CS202");
        m2.setCourseName("Operating Systems");
        m2.setFacultyId(2);

        when(moduleRepo.findAll()).thenReturn(List.of(m1, m2));

        List<Module> result = moduleController.getAllModule();

        assertThat(result).allMatch(Objects::nonNull);
    }

    @Test
    @DisplayName("Should return module with correct field values")
    void getAllModule_returnsCorrectFields() {
        Module module = new Module();
        module.setCourseCode("CS301");
        module.setCourseName("Networks");
        module.setFacultyId(3);

        when(moduleRepo.findAll()).thenReturn(List.of(module));

        List<Module> result = moduleController.getAllModule();

        Module returned = result.get(0);
        assertThat(returned.getCourseCode()).isEqualTo("CS301");
        assertThat(returned.getCourseName()).isEqualTo("Networks");
        assertThat(returned.getFacultyId()).isEqualTo(3);
    }
}
