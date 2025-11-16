package org.example.controller;

import org.example.entity.User;
import org.example.service.UserService;
import org.example.service.UserServiceImpl;

import java.time.LocalDateTime;

public class UserControllerImpl implements UserController{
    private final UserService userService = new UserServiceImpl();

    @Override
    public User createUser(String name, String email, Integer age, LocalDateTime created_at) {
        return userService.createUser(name, email, age, created_at);
    }

    @Override
    public User readUser(Long id) {
        return userService.readUser(id);
    }

    @Override
    public void updateUser(Long id, Integer age) {
        userService.updateUser(id, age);
    }

    @Override
    public void deleteUser(Long id) {
        userService.deleteUser(id);
    }
}
