package com.managment.global_Trans_App.repository;

import com.managment.global_Trans_App.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
    Optional<Route> findById(int id);
}
