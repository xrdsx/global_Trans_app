package com.managment.global_Trans_App.repository;

import com.managment.global_Trans_App.model.DayWork;
import com.managment.global_Trans_App.model.WorkDayHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkDayHoursRepository extends JpaRepository<WorkDayHours, Integer> {
    Optional<WorkDayHours> findByRouteIdAndDriverId(int routeId, long driverId);
    List<WorkDayHours> findByDayWorkIdAndEndTimeIsNull(Long dayWorkId);

    long countByDayWorkIdAndEndTimeIsNull(Long dayWorkId);
    WorkDayHours findByDayWorkId(long dayWorkId);

    Optional<WorkDayHours> findByDriverIdAndRouteIdAndDayWorkAndStartTimeIsNotNullAndEndTimeIsNull(Long driverId, int routeId, DayWork dayWork);
}
