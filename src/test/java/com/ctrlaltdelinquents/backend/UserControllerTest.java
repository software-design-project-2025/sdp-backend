package com.ctrlaltdelinquents.backend;
import com.ctrlaltdelinquents.backend.controller.UserController;
//import com.ctrlaltdelinquents.backend.model.User;
import com.ctrlaltdelinquents.backend.repo.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
//import org.assertj.core.api.Assertions;
//import org.springframework.test.web.servlet.MockMvc;

//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;

//import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
@Disabled
public class UserControllerTest {
    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    /**@Test

    @DisplayName("Check if there's only one user in the database")
    void checkIfThereIsOnlyOneUserInTheDatabase() {
        when(userRepo.findAll()).thenReturn(
                List.of(new UserModel("Not Monare"))
        );
        List<UserModel> users = userController.getAllUsers();

        assertThat(users).hasSize(1);
    }**/

}
