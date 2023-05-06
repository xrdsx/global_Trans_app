package com.managment.global_Trans_App.controller;

import com.managment.global_Trans_App.model.DayWork;
import com.managment.global_Trans_App.model.Driver;
import com.managment.global_Trans_App.model.Route;
import com.managment.global_Trans_App.model.WorkDayHours;
import com.managment.global_Trans_App.service.DayWorkService;
import com.managment.global_Trans_App.service.DriverService;
import com.managment.global_Trans_App.service.RouteService;
import com.managment.global_Trans_App.service.WorkDayHoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
public class WorkDayHoursController {

    @Autowired
    private WorkDayHoursService workDayHoursService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private DayWorkService dayWorkService;

    @PostMapping("/startRoute")
    public String startRoute(@RequestParam("routeId") int routeId, @RequestParam("driverId") int driverId) {
        WorkDayHours workDayHours = new WorkDayHours();
        Route route = routeService.findRouteById(routeId);
        workDayHours.setRoute(route);
        Driver driver = driverService.findDriverById((long) driverId);
        workDayHours.setDriver(driver);
        workDayHours.setStartTime(LocalDateTime.now());

        LocalDate currentDate = LocalDate.now();
        DayWork dayWork = dayWorkService.findDayWorkByDriverAndRouteAndDate((long) driverId, routeId, currentDate);
        if (dayWork == null) {
            // Utwórz nowy obiekt DayWork, jeśli nie istnieje
            dayWork = new DayWork();
            dayWork.setDate(currentDate);
            dayWork.setRoute(route);
            dayWork.setDriver(driver);
            //dayWork.setVehicle(vehicle); // Uzyskaj pojazd dla tego kierowcy
            dayWorkService.saveDayWork(dayWork);
        }

        workDayHours.setDayWork(dayWork);
        workDayHoursService.saveWorkDayHours(workDayHours);

        return "redirect:/dashboard";
    }

    @PostMapping("/finishRoute")
    public String finishRoute(@RequestParam("routeId") int routeId, @RequestParam("driverId") long driverId, @RequestParam("dayWorkId") long dayWorkId) {
        // Find the DayWork object based on the given parameters

        DayWork dayWork = dayWorkService.findDayWorkByDriverAndRouteAndDate(driverId, routeId, LocalDate.now());
        dayWork.setId(dayWork.getId());
        if (dayWork == null) {
            // If there's no DayWork object, return an error message or redirect to an error page
            return "error";
        }

        // Find the WorkDayHours object associated with the given DayWork object
        WorkDayHours workDayHours = workDayHoursService.findByDayWorkId(dayWorkId);
        if (workDayHours == null) {
            // If there's no WorkDayHours object, return an error message or redirect to an error page
            return "error";
        }


        // Update the endTime column
        workDayHours.setEndTime(LocalDateTime.now());
        workDayHoursService.saveWorkDayHours(workDayHours);

        return "redirect:/dashboard";
    }





}