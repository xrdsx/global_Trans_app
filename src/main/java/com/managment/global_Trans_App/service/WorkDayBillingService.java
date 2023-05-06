package com.managment.global_Trans_App.service;

import com.managment.global_Trans_App.model.WorkDayBilling;
import com.managment.global_Trans_App.repository.WorkDayBillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class WorkDayBillingService {

    @Autowired
    private WorkDayBillingRepository workDayBillingRepository;

    public void saveWorkDayBilling(WorkDayBilling workDayBilling) {
        workDayBillingRepository.save(workDayBilling);
    }

    public List<WorkDayBilling> findAll() {
        return workDayBillingRepository.findAll();
    }

    public void save(WorkDayBilling workDayBilling) {
        workDayBillingRepository.save(workDayBilling);
    }
    public BigDecimal getTotalKm(Long driverId, LocalDate dateFrom, LocalDate dateTo) {
        return workDayBillingRepository.getTotalKm(driverId, dateFrom, dateTo);
    }
}
