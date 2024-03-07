package org.example.task_3_1_5.service;

import org.example.task_3_1_5.model.Role;
import org.example.task_3_1_5.repository.RoleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    @Autowired
    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleDao.findAll();
    }

    @Override
    public void addRole(Role role) {
        roleDao.save(role);
    }

    @Override
    public void updateRole(Role role) {
        roleDao.save(role);
    }

    @Override
    public Role findByID(Long id) {
        Optional<Role> optional = roleDao.findById(id);
        return optional.orElse(null);
    }

    @Override
    public Role findByName(String name) {
        Optional<Role> optional = Optional.ofNullable(roleDao.findByName(name));
        if (optional.isEmpty()) {
            addRole(new Role(name));
            return roleDao.findByName(name);
        }
        return optional.get();
    }
}