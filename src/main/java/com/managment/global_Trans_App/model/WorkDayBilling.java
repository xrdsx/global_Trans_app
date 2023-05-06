package com.managment.global_Trans_App.model;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "WorkDayBilling")
public class WorkDayBilling {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "day_work_id", nullable = false)
    private int dayWorkId;

    @Column(name = "driver_id", nullable = false)
    private Long driverId;

    @Column(name = "total_km", nullable = false)
    private int totalKm;

    @Temporal(TemporalType.DATE)
    @Column(name = "work_date")
    private LocalDate workDate;

    // Add getters and setters for the fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDayWorkId() {
        return dayWorkId;
    }

    public void setDayWorkId(int dayWorkId) {
        this.dayWorkId = dayWorkId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public int getTotalKm() {
        return totalKm;
    }

    public void setTotalKm(int totalKm) {
        this.totalKm = totalKm;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }
}