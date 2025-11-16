package org.example.controller;

import org.example.entity.User;

import java.time.LocalDateTime;

public interface UserController {
    User createUser(String name, String email, Integer age, LocalDateTime created_at);
    User readUser(Long id);
    void updateUser(Long id, Integer age);
    void deleteUser(Long id);
}
