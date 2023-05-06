package com.managment.global_Trans_App.service;

import com.managment.global_Trans_App.model.Driver;
import com.managment.global_Trans_App.model.User;
import com.managment.global_Trans_App.repository.DriverRepository;
import com.managment.global_Trans_App.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    DriverRepository driverRepository;

    public String getCurrentUsername(HttpSession session) {
        return (String) session.getAttribute("username");
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }


    public boolean isUsernameUnique(String username) {
        return !userRepository.existsByUsername(username);
    }
}
