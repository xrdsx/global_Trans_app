package com.managment.global_Trans_App.repository;

import com.managment.global_Trans_App.model.WorkDayBilling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface WorkDayBillingRepository extends JpaRepository<WorkDayBilling, Integer> {
    WorkDayBilling findByDriverIdAndWorkDate(Long driverId, LocalDate workDate);
    List<WorkDayBilling> findAllByDriverIdAndWorkDateBetween(Long driverId, LocalDate dateFrom, LocalDate dateTo);

    @Query("SELECT SUM(w.totalKm) FROM WorkDayBilling w WHERE w.driverId = :driverId AND w.workDate BETWEEN :dateFrom AND :dateTo")
    BigDecimal getTotalKm(@Param("driverId") Long driverId, @Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);
}
