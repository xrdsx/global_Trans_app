package com.managment.global_Trans_App.service;

import com.managment.global_Trans_App.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean isUsernameUnique(String username) {
        return !userRepository.existsByUsername(username);
    }
}
