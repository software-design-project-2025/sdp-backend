// package com.ctrlaltdelinquents.backend.controller;

// import com.ctrlaltdelinquents.backend.model.Module;
// import com.ctrlaltdelinquents.backend.repo.ModuleRepo;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;

// import java.util.List;
// import java.util.Objects;

// import static org.assertj.core.api.Assertions.assertThat;
// import static org.mockito.Mockito.*;
// class ModuleControllerTest {

//     private ModuleRepo moduleRepo;
//     private ModuleController moduleController;

//     @BeforeEach
//     void setUp() {
//         moduleRepo = mock(ModuleRepo.class);
//         moduleController = new ModuleController(moduleRepo);
//     }

//     @Test
//     @DisplayName("Should return all modules when repository has entries")
//     void getAllModule_returnsAllModules() {
//         Module module1 = new Module();
//         module1.setCourseCode("CS101");
//         module1.setCourseName("Computer Science");
//         module1.setFacultyId("F1");

//         Module module2 = new Module();
//         module2.setCourseCode("ME101");
//         module2.setCourseName("Mechanical Engineering");
//         module2.setFacultyId("F2");

//         when(moduleRepo.findAll()).thenReturn(List.of(module1, module2));

//         List<Module> result = moduleController.getAllModule();

//         assertThat(result).hasSize(2);
//         assertThat(result.get(0).getCourseCode()).isEqualTo("CS101");
//         assertThat(result.get(1).getCourseName()).isEqualTo("Mechanical Engineering");
//         verify(moduleRepo, times(1)).findAll();
//     }

//     @Test
//     @DisplayName("Should return empty list when repository has no modules")
//     void getAllModule_returnsEmptyList_whenNoModulesExist() {
//         when(moduleRepo.findAll()).thenReturn(List.of());

//         List<Module> result = moduleController.getAllModule();

//         assertThat(result).isEmpty();
//         verify(moduleRepo, times(1)).findAll();
//     }

//     @Test
//     @DisplayName("Should call repository findAll exactly once")
//     void getAllModule_callsRepositoryOnce() {
//         when(moduleRepo.findAll()).thenReturn(List.of());
//         moduleController.getAllModule();
//         verify(moduleRepo, times(1)).findAll();
//     }

//     @Test
//     @DisplayName("Should return correct course codes and names")
//     void getAllModule_returnsCorrectModuleData() {
//         Module module = new Module();
//         module.setCourseCode("CS201");
//         module.setCourseName("Data Structures");
//         module.setFacultyId("F3");

//         when(moduleRepo.findAll()).thenReturn(List.of(module));

//         List<Module> result = moduleController.getAllModule();

//         assertThat(result).hasSize(1);
//         assertThat(result.get(0).getCourseCode()).isEqualTo("CS201");
//         assertThat(result.get(0).getCourseName()).isEqualTo("Data Structures");
//         assertThat(result.get(0).getFacultyId()).isEqualTo("F3");
//     }

//     @Test
//     @DisplayName("Should return Module instances from repository")
//     void getAllModule_returnsCorrectEntityType() {
//         Module module = new Module();
//         module.setCourseCode("EE101");
//         module.setCourseName("Electronics");
//         module.setFacultyId("F4");

//         when(moduleRepo.findAll()).thenReturn(List.of(module));

//         List<Module> result = moduleController.getAllModule();

//         assertThat(result).allMatch(Objects::nonNull);
//     }
// }
