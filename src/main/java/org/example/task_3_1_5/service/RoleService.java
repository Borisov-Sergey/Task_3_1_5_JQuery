package org.example.task_3_1_5.service;

import org.example.task_3_1_5.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    void addRole(Role role);

    void updateRole(Role role);

    Role findByID(Long id);

    Role findByName(String name);

}
