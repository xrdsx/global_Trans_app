package com.managment.global_Trans_App.service;

import com.managment.global_Trans_App.model.DayWork;
import com.managment.global_Trans_App.model.Driver;
import com.managment.global_Trans_App.model.WorkDayBilling;
import com.managment.global_Trans_App.repository.DayWorkRepository;
import com.managment.global_Trans_App.repository.WorkDayBillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DayWorkService {

    @Autowired
    private DayWorkRepository dayWorkRepository;
    @Autowired
    private WorkDayBillingRepository workDayBillingRepository;

    public List<DayWork> getAllDayWorks() {
        return dayWorkRepository.findAll();
    }

    public Optional<DayWork> getDayWorkById(int id) {
        return dayWorkRepository.findById(id);
    }

    public void saveDayWork(DayWork dayWork) {
        dayWorkRepository.save(dayWork);
    }

    public void deleteDayWork(int id) {
        dayWorkRepository.deleteById(id);
    }
    public List<DayWork> getAssignedRoutesByDriver(Driver driver) {
        return dayWorkRepository.findByDriver(driver);
    }

    public WorkDayBilling findWorkDayBillingByDriverIdAndWorkDate(Long driverId, LocalDate workDate) {
        return workDayBillingRepository.findByDriverIdAndWorkDate(driverId, workDate);
    }

    public void updateTotalKm(int id, int totalKm) {
        // Znajdź WorkDayBilling na podstawie id
        WorkDayBilling workDayBilling = workDayBillingRepository.findById(id).orElse(null);

        // Jeśli istnieje WorkDayBilling z tym id, zaktualizuj wartość total_km
        if (workDayBilling != null) {
            workDayBilling.setTotalKm(totalKm);
            workDayBillingRepository.save(workDayBilling);
        }
    }

    public void saveWorkDayBilling(WorkDayBilling workDayBilling) {
        workDayBillingRepository.save(workDayBilling);
    }

    public DayWork findDayWorkByDriverAndRouteAndDate(Long driverId, int routeId, LocalDate date) {
        return dayWorkRepository.findDayWorkByDriverIdAndRouteIdAndDate(driverId, routeId, date);
    }

    public DayWork findDayWorkById(Integer id) {
        return dayWorkRepository.findById(id).orElse(null);
    }
}