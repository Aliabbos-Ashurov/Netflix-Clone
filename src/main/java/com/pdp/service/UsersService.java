package com.pdp.service;

import com.pdp.dto.SignUpDTO;
import com.pdp.model.Users;
import com.pdp.repository.UsersRepository;
import com.pdp.service.base.BaseService;
import com.pdp.service.base.DTOSavingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Aliabbos Ashurov
 * @since 05/August/2024  13:51
 **/
@Service
public class UsersService implements BaseService<Users, Integer>, DTOSavingService<SignUpDTO, Integer> {

    private final UsersRepository repository;

    @Autowired
    public UsersService(UsersRepository repository) {
        this.repository = repository;
    }

    @Override
    public Integer saveFromDTO(SignUpDTO dto) {
        return repository.saveFromDTO(dto);
    }

    @Override
    public Integer save(Users users) {
        return repository.save(users);
    }

    @Override
    public void delete(Integer integer) {
        repository.delete(integer);
    }

    @Override
    public Users findById(Integer integer) {
        return repository.findById(integer);
    }

    @Override
    public List<Users> findAll() {
        return repository.findAll();
    }
}
