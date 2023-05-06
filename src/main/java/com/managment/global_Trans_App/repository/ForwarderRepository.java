package com.managment.global_Trans_App.repository;

import com.managment.global_Trans_App.model.Forwarder;
import com.managment.global_Trans_App.model.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForwarderRepository extends JpaRepository<Forwarder, Integer> {
    List<Forwarder> findAllByUser_Role(RoleType roleType);

}
