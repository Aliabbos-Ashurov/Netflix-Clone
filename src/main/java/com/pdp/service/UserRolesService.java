package com.pdp.service;

import com.pdp.model.Role;
import com.pdp.model.UserRoles;
import com.pdp.repository.UserRolesRepository;
import com.pdp.service.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * @author Aliabbos Ashurov
 * @since 06/August/2024  14:50
 **/
@Repository
public class UserRolesService implements BaseService<UserRoles, Integer> {

    private final UserRolesRepository repository;

    @Autowired
    public UserRolesService(UserRolesRepository repository) {
        this.repository = repository;
    }

    public Set<Role> getUserRoles(Integer userId) {
        return repository.getUserRoles(userId);
    }

    @Override
    public Integer save(UserRoles userRoles) {
        return repository.save(userRoles);
    }

    @Override
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public UserRoles findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<UserRoles> findAll() {
        return repository.findAll();
    }
}
