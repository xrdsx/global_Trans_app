package com.managment.global_Trans_App.service;

import com.managment.global_Trans_App.model.DayWork;
import com.managment.global_Trans_App.model.WorkDayHours;
import com.managment.global_Trans_App.repository.WorkDayHoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkDayHoursService {
    @Autowired
    private WorkDayHoursRepository workDayHoursRepository;

    public void saveWorkDayHours(WorkDayHours workDayHours) {
        workDayHoursRepository.save(workDayHours);
    }
    public WorkDayHours findById(int id) {
        return workDayHoursRepository.findById(id).orElse(null);
    }
    public WorkDayHours findByRouteIdAndDriverId(int routeId, long driverId) {
        return workDayHoursRepository.findByRouteIdAndDriverId(routeId, driverId).orElse(null);


    }
    public List<WorkDayHours> findInProgressByDayWorkId(Long dayWorkId) {
        return workDayHoursRepository.findByDayWorkIdAndEndTimeIsNull(dayWorkId);
    }

    public long countInProgressByDayWorkId(long dayWorkId) {
        return workDayHoursRepository.countByDayWorkIdAndEndTimeIsNull(dayWorkId);
    }
    public WorkDayHours findWorkDayHoursByDriverAndRouteAndDayWorkAndStartTimeNotNullAndEndTimeNull(Long driverId, int routeId, DayWork dayWork) {
        return workDayHoursRepository.findByDriverIdAndRouteIdAndDayWorkAndStartTimeIsNotNullAndEndTimeIsNull(driverId, routeId, dayWork).orElse(null);
    }

    public WorkDayHours findByDayWorkId(long dayWorkId) {
        return workDayHoursRepository.findByDayWorkId(dayWorkId);
    }
}