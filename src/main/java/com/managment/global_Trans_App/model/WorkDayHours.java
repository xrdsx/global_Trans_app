package com.managment.global_Trans_App.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class WorkDayHours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @ManyToOne
    @JoinColumn(name = "day_work_id")
    private DayWork dayWork;

    public DayWork getDayWork() {
        return dayWork;
    }

    public void setDayWork(DayWork dayWork) {
        this.dayWork = dayWork;
    }


    // konstruktor domyślny
    public WorkDayHours() {
    }





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}