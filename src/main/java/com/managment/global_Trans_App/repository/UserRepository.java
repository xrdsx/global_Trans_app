package com.managment.global_Trans_App.repository;



import com.managment.global_Trans_App.model.RoleType;
import com.managment.global_Trans_App.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    List<User> findAllByRole(RoleType role);
}


