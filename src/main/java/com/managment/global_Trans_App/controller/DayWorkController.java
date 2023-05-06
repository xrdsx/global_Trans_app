package com.managment.global_Trans_App.controller;

import com.managment.global_Trans_App.model.*;
import com.managment.global_Trans_App.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/daywork-management")
public class DayWorkController {

    @Autowired
    private DayWorkService dayWorkService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private UserService userService;

    @Autowired
    VehicleService vehicleService;



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

        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        model.addAttribute("vehicles", vehicles);



        return "add-daywork";
    }

    @PostMapping("/save")
    public String saveDayWork(@ModelAttribute("dayWork") DayWork dayWork) {
        // Zapisz dayWork
        dayWork.setStatus("Nie rozpoczęto");
        dayWorkService.saveDayWork(dayWork);

        // Pobierz driverId i workDate z dayWork
        Long driverId = dayWork.getDriver().getId();
        LocalDate workDate = dayWork.getDate();

        int routeId = dayWork.getRoute().getId();
        Route route = routeService.findRouteById(routeId);


        // Pobierz forwarder_km z trasy
        int forwarderKm = route.getForwarder_km();

        // Sprawdź, czy istnieje WorkDayBilling z tą samą datą i tym samym kierowcą
        WorkDayBilling existingWorkDayBilling = dayWorkService.findWorkDayBillingByDriverIdAndWorkDate(driverId, workDate);

        if (existingWorkDayBilling != null) {
            // Jeśli istnieje, zaktualizuj wartość total_km
            int updatedTotalKm = existingWorkDayBilling.getTotalKm() + forwarderKm;
            dayWorkService.updateTotalKm(existingWorkDayBilling.getId(), updatedTotalKm);
        } else {
            // Jeśli nie istnieje, utwórz nowy WorkDayBilling z odpowiednimi wartościami
            WorkDayBilling newWorkDayBilling = new WorkDayBilling();
            newWorkDayBilling.setDayWorkId(dayWork.getId());
            newWorkDayBilling.setDriverId(driverId);
            newWorkDayBilling.setWorkDate(workDate);
            newWorkDayBilling.setTotalKm(forwarderKm);

            // Zapisz nowy WorkDayBilling
            dayWorkService.saveWorkDayBilling(newWorkDayBilling);
        }

        return "redirect:/daywork-management";
    }



    @GetMapping("/delete/{id}")
    public String deleteDayWork(@PathVariable(value = "id") int id) {
        dayWorkService.deleteDayWork(id);
        return "redirect:/daywork-management";
    }

    @GetMapping("/assigned-routes")
    public String assignedRoutes(Model model, Principal principal) {
        String username = "zmienionylogin";
        User user = userService.findUserByUsername(username);
        List<Driver> drivers = driverService.getAllDriversByUser(user);
        List<DayWork> dayWorks = dayWorkService.getAllDayWorks()
                .stream()
                .filter(dw -> drivers.contains(dw.getDriver()))
                .toList();
        LocalDate todayDate = LocalDate.now();
        model.addAttribute("dayWorks", dayWorks);
        model.addAttribute("todayDate", todayDate);
        return "assigned-routes";
    }
    @GetMapping("/detail/{id}")
    public String dayWorkDetail(@PathVariable(value = "id") int id, Model model) {
        Optional<DayWork> dayWorkOptional = dayWorkService.getDayWorkById(id);

        if (dayWorkOptional.isPresent()) {
            DayWork dayWork = dayWorkOptional.get();
            model.addAttribute("dayWork", dayWork);

            Driver driver = dayWork.getDriver();
            model.addAttribute("driver", driver);

            Route route = dayWork.getRoute();
            model.addAttribute("route", route);

            // You can add any other related information here as well

            return "daywork-detail";
        } else {
            // Handle the case when the DayWork object is not found
            return "redirect:/daywork-management";
        }
    }

    @GetMapping("/drivers-by-date")
    @ResponseBody
    public List<Driver> getDriversByDate(@RequestParam("date") String date) {
        LocalDate selectedDate = LocalDate.parse(date);
        // Implement your logic to fetch available drivers based on the selected date
        return driverService.getAvailableDriversByDate(selectedDate);
    }

    @GetMapping("/vehicles-by-date")
    @ResponseBody
    public List<Vehicle> getVehiclesByDate(@RequestParam("date") String date) {
        LocalDate selectedDate = LocalDate.parse(date);
        // Implement your logic to fetch available vehicles based on the selected date
        return vehicleService.getAvailableVehiclesByDate(selectedDate);
    }

    @GetMapping("/routes-by-date")
    @ResponseBody
    public List<Route> getRoutesByDate(@RequestParam("date") String date) {
        LocalDate selectedDate = LocalDate.parse(date);
        // Implement your logic to fetch available routes based on the selected date
        return routeService.getAvailableRoutesByDate(selectedDate);
    }
}