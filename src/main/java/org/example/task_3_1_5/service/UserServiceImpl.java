package org.example.task_3_1_5.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.example.task_3_1_5.model.User;
import org.example.task_3_1_5.repository.UserDao;
import org.example.task_3_1_5.security.AuthProvider;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final AuthProvider authProvider;

    @Autowired
    public UserServiceImpl(UserDao userDao, AuthProvider authProvider) {
        this.userDao = userDao;
        this.authProvider = authProvider;
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAll() {
        return userDao.findAll();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void update(User user) {
        if (ObjectUtils.isEmpty(user.getPassword())) {
            userDao.findById(user.getId()).map(User::getPassword).ifPresent(user::setPassword);
            userDao.save(user);
            return;
        }
        user.setPassword(authProvider.getPasswordEncoder().encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void removeById(Long id) {
        User user = userDao.getById(id);
        userDao.delete(user);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public User getById(Long id) {
        return userDao.findById(id).get();
    }

    @Override
    public User findByUserName(String username) {
        return userDao.findByUserName(username);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

}