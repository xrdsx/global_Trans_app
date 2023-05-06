package com.managment.global_Trans_App.repository;

import com.managment.global_Trans_App.model.Driver;
import com.managment.global_Trans_App.model.RoleType;
import com.managment.global_Trans_App.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Long> {
    Driver findByUser_Username(String username);

    List<Driver> findByUser(User user);
    List<Driver> findByUser_Id(Long userId);
    Driver findByPhoneNumber(String phoneNumber);
    List<Driver> findAllByUser_Role(RoleType roleType);

}