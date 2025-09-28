package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.dto.UserProgressStats;
import com.ctrlaltdelinquents.backend.model.User;
import com.ctrlaltdelinquents.backend.repo.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private UserRepository userRepository;
    private UserController userController;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userController = new UserController(userRepository);
    }

    @Test
    @DisplayName("Should create a new user and return it")
    void createUser_returnsCreatedUser() {
        User user = new User();
        user.setUserid("supabase123");
        user.setRole("student");
        user.setBio("Test User");

        when(userRepository.findByUserId("supabase123")).thenReturn(Collections.emptyList());
        when(userRepository.save(user)).thenReturn(user);

        User response = userController.createUser(user);

        assertThat(response).isEqualTo(user);
        verify(userRepository, times(1)).findByUserId("supabase123");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("Should return existing user if user already exists")
    void createUser_returnsExistingUser() {
        User existingUser = new User();
        existingUser.setUserid("supabase123");
        existingUser.setRole("student");
        existingUser.setBio("Existing User");

        User newUser = new User();
        newUser.setUserid("supabase123");
        newUser.setRole("teacher");
        newUser.setBio("New User");

        when(userRepository.findByUserId("supabase123")).thenReturn(List.of(existingUser));

        User response = userController.createUser(newUser);

        assertThat(response).isEqualTo(existingUser);
        verify(userRepository, times(1)).findByUserId("supabase123");
        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should return all users when users exist")
    void getAllUsers_returnsUsers() {
        User user1 = new User();
        user1.setUserid("user1");
        user1.setRole("student");

        User user2 = new User();
        user2.setUserid("user2");
        user2.setRole("teacher");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        ResponseEntity<?> response = userController.getAllUsers();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) response.getBody();
        assertThat(users).containsExactly(user1, user2);
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return 404 when no users exist")
    void getAllUsers_returnsNotFoundWhenEmpty() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = userController.getAllUsers();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Error: No users found");
        verify(userRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return 500 when database error occurs in getAllUsers")
    void getAllUsers_returnsInternalServerError() {
        when(userRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<?> response = userController.getAllUsers();

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).asString().contains("Error: Failed to fetch users");
    }

    @Test
    @DisplayName("Should return user by ID if exists")
    void getUserById_returnsUser() {
        User user = new User();
        user.setUserid("user123");
        user.setRole("student");

        when(userRepository.findByUserId("user123")).thenReturn(List.of(user));

        ResponseEntity<?> response = userController.getUserById("user123");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) response.getBody();
        assertThat(users).containsExactly(user);
        verify(userRepository, times(1)).findByUserId("user123");
    }

    @Test
    @DisplayName("Should return 404 when user by ID does not exist")
    void getUserById_returnsNotFound() {
        when(userRepository.findByUserId("unknown")).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = userController.getUserById("unknown");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isEqualTo("Error: User with id unknown" + "not found");
        verify(userRepository, times(1)).findByUserId("unknown");
    }

    @Test
    @DisplayName("Should return 500 when database error occurs in getUserById")
    void getUserById_returnsInternalServerError() {
        when(userRepository.findByUserId("user123")).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<?> response = userController.getUserById("user123");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).asString().contains("Error: Failed to fetch user user123 by ID");
    }

    @Test
    @DisplayName("Should return 500 when error occurs getting progress stats")
    void userGetProgressStats_returnsInternalServerError() {
        when(userRepository.userGetProgressStats("user123")).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<UserProgressStats> response = userController.userGetProgressStats("user123");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNull();
        verify(userRepository, times(1)).userGetProgressStats("user123");
    }
}