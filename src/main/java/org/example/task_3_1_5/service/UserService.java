package org.example.task_3_1_5.service;

import org.example.task_3_1_5.model.User;

import java.util.List;

public interface UserService {
    void save(User user);

    List<User> getAll();

    void update(User user);

    void removeById(Long id);

    User getById(Long id);

    User findByUserName(String username);

    User findByEmail(String email);
}
