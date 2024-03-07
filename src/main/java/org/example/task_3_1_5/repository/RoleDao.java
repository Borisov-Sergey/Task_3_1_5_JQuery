package org.example.task_3_1_5.repository;


import org.example.task_3_1_5.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<Role, Long> {
    Role findByName(String name);
}