package com.pdp.service;

import com.pdp.dto.LoginDTO;
import com.pdp.dto.SignUpDTO;
import com.pdp.model.Users;
import com.pdp.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

/**
 * @author Aliabbos Ashurov
 * @since 05/August/2024  13:41
 **/
@Service
public class AuthService {

    private final AuthRepository repository;

    @Autowired
    public AuthService(AuthRepository repository) {
        this.repository = repository;
    }

    public void blockUser(Integer userId) {
        repository.blockUser(userId);
    }

    public Users login(LoginDTO dto) {
        return repository.login(dto);
    }

    public Users signUp(SignUpDTO dto) {
        return repository.signUp(dto);
    }

    public Users findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
