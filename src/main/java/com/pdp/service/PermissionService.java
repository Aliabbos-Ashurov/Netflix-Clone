package com.pdp.service;

import com.pdp.model.Permission;
import com.pdp.repository.PermissionRepository;
import com.pdp.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 05/August/2024  13:50
 **/
@Service
public class PermissionService implements BaseService<Permission, Integer> {

    private final PermissionRepository repository;

    @Autowired
    public PermissionService(PermissionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Integer save(Permission permission) {
        return repository.save(permission);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Permission findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Permission> findAll() {
        return repository.findAll();
    }
}
