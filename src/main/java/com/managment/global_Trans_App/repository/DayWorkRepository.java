package com.managment.global_Trans_App.repository;

import com.managment.global_Trans_App.model.DayWork;
import com.managment.global_Trans_App.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DayWorkRepository extends JpaRepository<DayWork, Integer> {
    List<DayWork> findByDriver(Driver driver);

    List<DayWork> findByDriverId(Long driverId);
    List<DayWork> findByVehicleId(Long vehicleId);
    List<DayWork> findByRouteId(Integer routeId);
    @Query("SELECT d FROM DayWork d WHERE d.driver.id = :driverId AND d.route.id = :routeId AND d.date = :date")
    DayWork findDayWorkByDriverIdAndRouteIdAndDate(@Param("driverId") Long driverId, @Param("routeId") int routeId, @Param("date") LocalDate date);
}
