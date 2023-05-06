package com.managment.global_Trans_App.service;

import com.managment.global_Trans_App.model.DayWork;
import com.managment.global_Trans_App.model.Vehicle;
import com.managment.global_Trans_App.repository.DayWorkRepository;
import com.managment.global_Trans_App.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    DayWorkRepository dayWorkRepository;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getVehicleById(Long id) {
        return vehicleRepository.findById(id);
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    public List<Vehicle> getAvailableVehiclesByDate(LocalDate date) {
        // Fetch all vehicles
        List<Vehicle> allVehicles = vehicleRepository.findAll();

        // Filter vehicles based on the selected date
        List<Vehicle> availableVehicles = allVehicles.stream()
                .filter(vehicle -> isVehicleAvailable(vehicle, date))
                .collect(Collectors.toList());

        return availableVehicles;
    }
    private boolean isVehicleAvailable(Vehicle vehicle, LocalDate date) {
        List<DayWork> vehicleDayWorks = dayWorkRepository.findByVehicleId(vehicle.getId());
        return vehicleDayWorks.stream().noneMatch(dayWork -> dayWork.getDate().equals(date));
    }
}