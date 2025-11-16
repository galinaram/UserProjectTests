package dao;

import org.example.dao.UserDAO;
import org.example.dao.UserDAOImpl;
import org.example.entity.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDAOImplTest extends BaseDatabaseTest {

    private final UserDAO userDAO = new UserDAOImpl();

    @Test
    @Order(1)
    void save_ShouldSaveUser_WhenValidUser() {
        User user = new User("John Doe", "john@example.com", 30, LocalDateTime.now());

        User savedUser = userDAO.save(user);

        assertNotNull(savedUser);
        assertNotNull(savedUser.getId());
        assertEquals("John Doe", savedUser.getName());
        assertEquals("john@example.com", savedUser.getEmail());
        assertEquals(30, savedUser.getAge());
    }

    @Test
    @Order(2)
    void findById_ShouldReturnUser_WhenUserExists() {
        User user = new User("Jane Doe", "jane@example.com", 25, LocalDateTime.now());
        User savedUser = userDAO.save(user);

        Optional<User> foundUser = userDAO.findById(savedUser.getId());

        assertTrue(foundUser.isPresent());
        assertEquals(savedUser.getId(), foundUser.get().getId());
        assertEquals("Jane Doe", foundUser.get().getName());
        assertEquals("jane@example.com", foundUser.get().getEmail());
        assertEquals(25, foundUser.get().getAge());
    }

    @Test
    @Order(3)
    void findById_ShouldReturnEmpty_WhenUserDoesNotExist() {
        Optional<User> foundUser = userDAO.findById(999L);

        assertTrue(foundUser.isEmpty());
    }

    @Test
    @Order(4)
    void update_ShouldUpdateUser_WhenUserExists() {
        User user = new User("Old Name", "old@example.com", 25, LocalDateTime.now());
        User savedUser = userDAO.save(user);

        savedUser.setName("New Name");
        savedUser.setEmail("new@example.com");
        savedUser.setAge(30);

        userDAO.update(savedUser);

        Optional<User> updatedUser = userDAO.findById(savedUser.getId());
        assertTrue(updatedUser.isPresent());
        assertEquals("New Name", updatedUser.get().getName());
        assertEquals("new@example.com", updatedUser.get().getEmail());
        assertEquals(30, updatedUser.get().getAge());
    }

    @Test
    @Order(5)
    void delete_ShouldDeleteUser_WhenUserExists() {
        User user = new User("To Delete", "delete@example.com", 40, LocalDateTime.now());
        User savedUser = userDAO.save(user);

        assertTrue(userDAO.findById(savedUser.getId()).isPresent());

        userDAO.delete(savedUser.getId());

        assertTrue(userDAO.findById(savedUser.getId()).isEmpty());
    }
}