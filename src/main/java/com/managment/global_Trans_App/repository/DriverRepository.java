package com.managment.global_Trans_App.repository;

import com.managment.global_Trans_App.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Long> {
}