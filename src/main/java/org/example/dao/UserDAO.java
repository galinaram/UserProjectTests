package org.example.dao;

import org.example.entity.User;

import java.util.Optional;

public interface UserDAO {
    User save(User user);
    Optional<User> findById(Long id);
    void update(User user);
    void delete(Long id);
}