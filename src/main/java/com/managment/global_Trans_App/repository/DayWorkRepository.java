package com.managment.global_Trans_App.repository;

import com.managment.global_Trans_App.model.DayWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DayWorkRepository extends JpaRepository<DayWork, Integer> {
    // dodatkowe metody, jeśli potrzebne
}
