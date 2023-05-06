package com.managment.global_Trans_App.repository;

import com.managment.global_Trans_App.model.ReportedIssue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportedIssueRepository extends JpaRepository<ReportedIssue, Long> {
    // metody związane z komunikacją z bazą danych
}
