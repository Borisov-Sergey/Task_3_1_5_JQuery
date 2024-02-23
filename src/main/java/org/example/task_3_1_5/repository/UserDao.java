package org.example.task_3_1_5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.task_3_1_5.model.User;


@Repository
public interface UserDao extends JpaRepository<User, Long> {
    User findByEmail(String email);

    User findByUserName(String username);

}
