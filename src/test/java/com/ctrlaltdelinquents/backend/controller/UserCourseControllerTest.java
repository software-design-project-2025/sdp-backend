package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.model.Module;
import com.ctrlaltdelinquents.backend.model.User;
import com.ctrlaltdelinquents.backend.repo.ModuleRepository;
import com.ctrlaltdelinquents.backend.repo.UserCourseRepository;
import com.ctrlaltdelinquents.backend.repo.UserRepo;
import com.ctrlaltdelinquents.backend.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserCourseControllerTest {

    private UserService userService;
    private UserRepo userRepo;
    private UserCourseRepository userCourseRepository;
    private ModuleRepository moduleRepository;
    private UserController userController;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        userRepo = mock(UserRepo.class);
        userCourseRepository = mock(UserCourseRepository.class);
        moduleRepository = mock(ModuleRepository.class);

        userController = new UserController(userService, userRepo);
        userController.userCourseRepository = userCourseRepository;
        userController.moduleRepository = moduleRepository;
    }

    @Test
    @DisplayName("Should create a user and return it")
    void createUser_returnsCreatedUser() {
        User user = new User("supabase123", "test@example.com", "Test User");
        when(userService.createUser(user)).thenReturn(user);

        ResponseEntity<User> response = userController.createUser(user);

        assertThat(response.getBody()).isEqualTo(user);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(userService, times(1)).createUser(user);
    }

    @Test
    @DisplayName("Should return user by Supabase ID if exists")
    void getUserBySupabaseId_returnsUser() {
        User user = new User("supabase123", "test@example.com", "Test User");
        when(userService.getUserBySupabaseId("supabase123")).thenReturn(Optional.of(user));

        ResponseEntity<User> response = userController.getUserBySupabaseId("supabase123");

        assertThat(response.getBody()).isEqualTo(user);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(userService, times(1)).getUserBySupabaseId("supabase123");
    }

    @Test
    @DisplayName("Should return 404 if user by Supabase ID does not exist")
    void getUserBySupabaseId_returnsNotFound() {
        when(userService.getUserBySupabaseId("unknown")).thenReturn(Optional.empty());

        ResponseEntity<User> response = userController.getUserBySupabaseId("unknown");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Should update user by Supabase ID")
    void updateUserBySupabaseId_returnsUpdatedUser() {
        User updates = new User("supabase123", "updated@example.com", "Updated User");
        when(userService.updateUser("supabase123", updates)).thenReturn(updates);

        ResponseEntity<User> response = userController.updateUserBySupabaseId("supabase123", updates);

        assertThat(response.getBody()).isEqualTo(updates);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(userService, times(1)).updateUser("supabase123", updates);
    }

    @Test
    @DisplayName("Should delete user by Supabase ID")
    void deleteUserBySupabaseId_returnsNoContent() {
        ResponseEntity<Void> response = userController.deleteUserBySupabaseId("supabase123");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        verify(userService, times(1)).deleteUser("supabase123");
    }

    @Test
    @DisplayName("Should return all users")
    void getAllUsers_returnsUsers() {
        User user1 = new User("supabase1", "a@example.com", "User A");
        User user2 = new User("supabase2", "b@example.com", "User B");
        when(userRepo.findAll()).thenReturn(List.of(user1, user2));

        List<User> result = userController.getAllUsers();

        assertThat(result).containsExactly(user1, user2);
        verify(userRepo, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return user by database ID if exists")
    void getUserById_returnsUser() {
        User user = new User("supabase1", "a@example.com", "User A");
        when(userRepo.findById(1)).thenReturn(Optional.of(user));

        ResponseEntity<?> response = userController.getUserById(1);

        assertThat(response.getBody()).isEqualTo(user);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Should return 404 when user by database ID does not exist")
    void getUserById_returnsNotFound() {
        when(userRepo.findById(99)).thenReturn(Optional.empty());

        ResponseEntity<?> response = userController.getUserById(99);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Should return user subjects if they exist")
    void getUserSubjects_returnsSubjects() {
        when(userCourseRepository.findCourseCodesByUserId(1)).thenReturn(List.of("CS101"));
        Module module = new Module();
        module.setCourseCode("CS101");
        module.setCourseName("Computer Science");
        when(moduleRepository.findByCourseCode("CS101")).thenReturn(module);

        ResponseEntity<?> response = userController.getUserSubjects(1);

        List<?> subjects = (List<?>) response.getBody();
        assertThat(subjects).hasSize(1);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("Should return 404 if user has no subjects")
    void getUserSubjects_returnsNotFoundWhenEmpty() {
        when(userCourseRepository.findCourseCodesByUserId(1)).thenReturn(List.of());

        ResponseEntity<?> response = userController.getUserSubjects(1);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
