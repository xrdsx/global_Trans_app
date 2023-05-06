package com.managment.global_Trans_App.controller;

import com.managment.global_Trans_App.model.Vehicle;
import com.managment.global_Trans_App.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/vehicle-management")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    public String getAllVehicles(Model model) {
        List<Vehicle> vehicles = vehicleService.getAllVehicles();
        model.addAttribute("vehicles", vehicles);
        return "vehicle-management";
    }

    @GetMapping("/{id}")
    public String getVehicleById(@PathVariable Long id, Model model) {
        Optional<Vehicle> vehicle = vehicleService.getVehicleById(id);
        if (vehicle.isPresent()) {
            model.addAttribute("vehicle", vehicle.get());
            return "vehicle-details";
        }
        return "error";
    }

    @GetMapping("/add")
    public String showAddVehicleForm(Model model) {
        model.addAttribute("vehicle", new Vehicle());
        return "add-vehicle";
    }

    @PostMapping("/add")
    public String addVehicle(@ModelAttribute("vehicle") Vehicle vehicle) {
        vehicleService.saveVehicle(vehicle);
        return "redirect:/vehicle-management";
    }

    @GetMapping("/edit/{id}")
    public String showEditVehicleForm(@PathVariable Long id, Model model) {
        Optional<Vehicle> vehicle = vehicleService.getVehicleById(id);
        if (vehicle.isPresent()) {
            model.addAttribute("vehicle", vehicle.get());
            return "edit-vehicle";
        }
        return "error";
    }

    @PostMapping("/edit")
    public String editVehicle(@ModelAttribute("vehicle") Vehicle vehicle) {
        vehicleService.saveVehicle(vehicle);
        return "redirect:/vehicle-management";
    }

    @GetMapping("/delete/{id}")
    public String deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicle(id);
        return "redirect:/vehicle-management";
    }


}