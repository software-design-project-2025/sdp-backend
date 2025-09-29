package com.ctrlaltdelinquents.backend.controller;

import com.ctrlaltdelinquents.backend.repo.UserCourseRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserCourseControllerTest {

    private final UserCourseRepository userCourseRepository = mock(UserCourseRepository.class);
    private final UserCourseController controller = new UserCourseController(userCourseRepository);

    @Test
    @DisplayName("Should return one course for user")
    void getAllUserCourses_returnsOneCourse() {
        when(userCourseRepository.findCourseCodesByUserId("1")).thenReturn(List.of("CS101"));

        ResponseEntity<?> response = controller.getAllUserCourses("1");

        assertThat(response.getStatusCode().value()).isEqualTo(200);

        Map<String, List<String>> body = (Map<String, List<String>>) response.getBody();
        assertThat(body).containsKey("courses");
        assertThat(body.get("courses")).containsExactly("CS101");
    }

    @Test
    @DisplayName("Should return multiple courses for user")
    void getAllUserCourses_returnsMultipleCourses() {
        when(userCourseRepository.findCourseCodesByUserId("2")).thenReturn(List.of("CS101", "CS102"));

        ResponseEntity<?> response = controller.getAllUserCourses("2");

        assertThat(response.getStatusCode().value()).isEqualTo(200);

        Map<String, List<String>> body = (Map<String, List<String>>) response.getBody();
        assertThat(body.get("courses")).containsExactly("CS101", "CS102");
    }

    @Test
    @DisplayName("Should return 404 when user has no courses")
    void getAllUserCourses_returnsNotFoundForEmptyList() {
        when(userCourseRepository.findCourseCodesByUserId("3")).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = controller.getAllUserCourses("3");

        assertThat(response.getStatusCode().value()).isEqualTo(404);

        Map<String, String> body = (Map<String, String>) response.getBody();
        assertThat(body.get("error")).isEqualTo("User has no courses with id 3");
    }

    @Test
    @DisplayName("Should return 404 when repository returns null")
    void getAllUserCourses_returnsNotFoundForNullList() {
        when(userCourseRepository.findCourseCodesByUserId("4")).thenReturn(null);

        ResponseEntity<?> response = controller.getAllUserCourses("4");

        assertThat(response.getStatusCode().value()).isEqualTo(404);

        Map<String, String> body = (Map<String, String>) response.getBody();
        assertThat(body.get("error")).isEqualTo("User has no courses with id 4");
    }

    @Test
    @DisplayName("Should return correct response format with 'courses' key")
    void getAllUserCourses_responseContainsCoursesKey() {
        when(userCourseRepository.findCourseCodesByUserId("5")).thenReturn(List.of("CS105"));

        ResponseEntity<?> response = controller.getAllUserCourses("5");

        assertThat(response.getStatusCode().value()).isEqualTo(200);

        Map<String, ?> body = (Map<String, ?>) response.getBody();
        assertThat(body).containsKey("courses");
    }
}
