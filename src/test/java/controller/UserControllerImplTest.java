package controller;

import org.example.controller.UserController;
import org.example.controller.UserControllerImpl;
import org.example.entity.User;
import org.example.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerImplTest {

    @Mock
    private UserService userService;

    private UserController userController;

    private User testUser;
    private final LocalDateTime testTime = LocalDateTime.of(2023, 1, 1, 10, 0);

    @BeforeEach
    void setUp() {
        userController = new UserControllerImpl(userService);
        testUser = new User("Test User", "test@example.com", 25, testTime);
        testUser.setId(1L);
    }

    @Test
    void createUser_ShouldCallService() {
        when(userService.createUser(anyString(), anyString(), anyInt(), any(LocalDateTime.class)))
                .thenReturn(testUser);

        User result = userController.createUser("Test", "test@test.com", 25, testTime);

        assertNotNull(result);
        verify(userService).createUser(eq("Test"), eq("test@test.com"), eq(25), eq(testTime));
    }

    @Test
    void createUser_ShouldCallServiceWithAnyParameters() {
        when(userService.createUser(anyString(), anyString(), anyInt(), any(LocalDateTime.class)))
                .thenReturn(testUser);

        User result = userController.createUser("Any Name", "any@email.com", 30, LocalDateTime.now());

        assertNotNull(result);
        verify(userService).createUser(anyString(), anyString(), anyInt(), any(LocalDateTime.class));
    }

    @Test
    void readUser_ShouldCallService() {
        when(userService.readUser(1L)).thenReturn(testUser);

        User result = userController.readUser(1L);

        assertNotNull(result);
        verify(userService).readUser(1L);
    }

    @Test
    void updateUser_ShouldCallService() {
        userController.updateUser(1L, 30);

        verify(userService).updateUser(1L, 30);
    }

    @Test
    void updateUser_ShouldCallServiceWithAnyParameters() {
        userController.updateUser(anyLong(), anyInt());

        verify(userService).updateUser(anyLong(), anyInt());
    }

    @Test
    void deleteUser_ShouldCallService() {
        userController.deleteUser(1L);

        verify(userService).deleteUser(1L);
    }

    @Test
    void deleteUser_ShouldCallServiceWithAnyParameter() {
        userController.deleteUser(anyLong());

        verify(userService).deleteUser(anyLong());
    }
}