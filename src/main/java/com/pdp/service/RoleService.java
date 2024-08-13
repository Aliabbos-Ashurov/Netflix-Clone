package com.pdp.service;

import com.pdp.model.Role;
import com.pdp.repository.RoleRepository;
import com.pdp.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 06/August/2024  14:49
 **/
@Service
public class RoleService implements BaseService<Role, Integer> {

    private final RoleRepository repository;

    @Autowired
    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Integer save(Role role) {
        return repository.save(role);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Role findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<Role> findAll() {
        return repository.findAll();
    }
}
