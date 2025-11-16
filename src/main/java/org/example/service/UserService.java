package org.example.service;

import org.example.entity.User;

import java.time.LocalDateTime;

public interface UserService {
    User createUser(String name, String email, Integer age, LocalDateTime created_at);
    User readUser(Long id);
    void updateUser(Long id, Integer age);
    void deleteUser(Long id);
}
