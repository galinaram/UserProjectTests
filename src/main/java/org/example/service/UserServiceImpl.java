package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.dao.UserDAO;
import org.example.dao.UserDAOImpl;
import org.example.entity.User;

import java.time.LocalDateTime;

@Slf4j
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO = new UserDAOImpl();

    @Override
    public User createUser(String name, String email, Integer age, LocalDateTime createdAt) {
        log.info("Create user: {}", name);
        User user = new User(name, email, age, createdAt);
        return userDAO.save(user);
    }

    @Override
    public User readUser(Long id) {
        log.info("Read user: {}", id);
        return userDAO.findById((long) id).orElse(null);
    }

    @Override
    public void updateUser(Long id, Integer age) {
        log.info("Update user: {}", id);
        User user = userDAO.findById((long) id).orElse(null);
        if (user != null) {
            user.setAge(age);
            userDAO.update(user);
        }
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Delete user: {}", id);
        userDAO.delete((long) id);
    }
}