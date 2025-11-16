package service;

import org.example.dao.UserDAO;
import org.example.entity.User;
import org.example.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDAO userDAO;

    private UserServiceImpl userService;

    @Captor
    private ArgumentCaptor<User> userCaptor;

    private User testUser;
    private final LocalDateTime testTime = LocalDateTime.of(2023, 1, 1, 10, 0);

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userDAO);

        testUser = new User("Test User", "test@example.com", 25, testTime);
        testUser.setId(1L);
    }

    @Test
    void createUser_ShouldCreateAndReturnUser_WhenValidParameters() {
        when(userDAO.save(any(User.class))).thenReturn(testUser);

        User result = userService.createUser("Test User", "test@example.com", 25, testTime);

        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        assertEquals("Test User", result.getName());
        assertEquals("test@example.com", result.getEmail());
        assertEquals(25, result.getAge());

        verify(userDAO).save(userCaptor.capture());
        User savedUser = userCaptor.getValue();
        assertEquals("Test User", savedUser.getName());
        assertEquals("test@example.com", savedUser.getEmail());
        assertEquals(25, savedUser.getAge());
        assertEquals(testTime, savedUser.getCreatedAt());
    }

    @Test
    void readUser_ShouldReturnUser_WhenUserExists() {
        when(userDAO.findById(1L)).thenReturn(Optional.of(testUser));

        User result = userService.readUser(1L);

        assertNotNull(result);
        assertEquals(testUser.getId(), result.getId());
        assertEquals("Test User", result.getName());
        verify(userDAO).findById(1L);
    }

    @Test
    void readUser_ShouldReturnNull_WhenUserDoesNotExist() {
        when(userDAO.findById(anyLong())).thenReturn(Optional.empty());

        User result = userService.readUser(999L);

        assertNull(result);
        verify(userDAO).findById(999L);
    }

    @Test
    void updateUser_ShouldUpdateUserAge_WhenUserExists() {
        User userToUpdate = new User("Test User", "test@example.com", 25, testTime);
        userToUpdate.setId(1L);

        when(userDAO.findById(1L)).thenReturn(Optional.of(userToUpdate));

        userService.updateUser(1L, 30);

        verify(userDAO).findById(1L);
        verify(userDAO).update(userCaptor.capture());

        User updatedUser = userCaptor.getValue();
        assertEquals(30, updatedUser.getAge());
        assertEquals("Test User", updatedUser.getName());
        assertEquals("test@example.com", updatedUser.getEmail());
    }

    @Test
    void updateUser_ShouldNotUpdate_WhenUserDoesNotExist() {
        when(userDAO.findById(999L)).thenReturn(Optional.empty());

        userService.updateUser(999L, 30);

        verify(userDAO).findById(999L);
        verify(userDAO, never()).update(any(User.class));
    }

    @Test
    void deleteUser_ShouldDeleteUser_WhenUserExists() {
        userService.deleteUser(1L);

        verify(userDAO).delete(1L);
    }

    @Test
    void deleteUser_ShouldCallDAODelete_WhenCalledWithAnyId() {
        userService.deleteUser(999L);

        verify(userDAO).delete(999L);
    }
}