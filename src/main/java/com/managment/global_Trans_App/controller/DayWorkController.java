package com.managment.global_Trans_App.controller;

import com.managment.global_Trans_App.model.DayWork;
import com.managment.global_Trans_App.model.Driver;
import com.managment.global_Trans_App.model.Route;
import com.managment.global_Trans_App.service.DayWorkService;
import com.managment.global_Trans_App.service.DriverService;
import com.managment.global_Trans_App.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/daywork-management")
public class DayWorkController {

    @Autowired
    private DayWorkService dayWorkService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private RouteService routeService;

    @GetMapping
    public String dayWorkList(Model model) {
        List<DayWork> dayWorks = dayWorkService.getAllDayWorks();
        model.addAttribute("dayWorks", dayWorks);
        return "daywork-management";
    }

    @GetMapping("/add")
    public String addDayWork(Model model) {
        DayWork dayWork = new DayWork();
        dayWork.setDate(LocalDate.now());
        model.addAttribute("dayWork", dayWork);

        List<Driver> drivers = driverService.getAllDrivers();
        model.addAttribute("drivers", drivers);

        List<Route> routes = routeService.getAllRoutes();
        model.addAttribute("routes", routes);

        return "add-daywork";
    }

    @PostMapping("/save")
    public String saveDayWork(@ModelAttribute("dayWork") DayWork dayWork) {
        dayWorkService.saveDayWork(dayWork);
        return "redirect:/daywork-management";
    }



    @GetMapping("/delete/{id}")
    public String deleteDayWork(@PathVariable(value = "id") int id) {
        dayWorkService.deleteDayWork(id);
        return "redirect:/daywork-management";
    }
}