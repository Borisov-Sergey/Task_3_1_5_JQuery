package org.example.task_3_1_5.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.example.task_3_1_5.model.User;
import org.example.task_3_1_5.repository.UserDao;
import org.example.task_3_1_5.security.SecurityUserDetails;

@Service
public class UserServiceDetailsImpl implements UserDetailsService {
    private final UserDao userDao;

    public UserServiceDetailsImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userDaoByEmail = userDao.findByEmail(username);
        User userDaoByUserName = userDao.findByUserName(username);

        if (userDaoByUserName != null) {
            return new SecurityUserDetails(userDaoByUserName);
        } else if (userDaoByEmail != null) {
            return new SecurityUserDetails(userDaoByEmail);
        } else {
            throw new UsernameNotFoundException("User not found!");
        }
    }
}
