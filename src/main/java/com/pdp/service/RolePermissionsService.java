package com.pdp.service;

import com.pdp.model.Permission;
import com.pdp.model.RolePermissions;
import com.pdp.repository.RolePermissionsRepository;
import com.pdp.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author Aliabbos Ashurov
 * @since 05/August/2024  13:51
 **/
@Service
public class RolePermissionsService implements BaseService<RolePermissions, Integer> {

    private final RolePermissionsRepository repository;

    public Set<Permission> getRolePermissions(Integer roleId) {
        return repository.getRolePermissions(roleId);
    }

    @Autowired
    public RolePermissionsService(RolePermissionsRepository repository) {
        this.repository = repository;
    }

    @Override
    public Integer save(RolePermissions rolePermissions) {
        return repository.save(rolePermissions);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public RolePermissions findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<RolePermissions> findAll() {
        return repository.findAll();
    }
}
