package org.example.task_3_1_5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.example.task_3_1_5.model.User;
import org.example.task_3_1_5.repository.UserDao;
import org.example.task_3_1_5.security.AuthProvider;

@Service
public class RegistrationServiceImpl {

    private final UserDao userDao;
    private final AuthProvider authProvider;

    @Autowired
    public RegistrationServiceImpl(UserDao userDao, AuthProvider authProvider) {
        this.userDao = userDao;
        this.authProvider = authProvider;
    }

    @Transactional
    public void register(User user) {
        user.setPassword(authProvider.getPasswordEncoder().encode(user.getPassword()));
        userDao.save(user);
    }

}