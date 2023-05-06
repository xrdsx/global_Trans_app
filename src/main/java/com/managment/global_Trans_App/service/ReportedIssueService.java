package com.managment.global_Trans_App.service;

import com.managment.global_Trans_App.model.ReportedIssue;
import com.managment.global_Trans_App.repository.ReportedIssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportedIssueService {

    @Autowired
    private ReportedIssueRepository reportedIssueRepository;

    public List<ReportedIssue> findAll() {
        return reportedIssueRepository.findAll();
    }
}
