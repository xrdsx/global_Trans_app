package com.managment.global_Trans_App.service;

import com.managment.global_Trans_App.model.DayWork;
import com.managment.global_Trans_App.model.Driver;
import com.managment.global_Trans_App.model.User;
import com.managment.global_Trans_App.repository.DayWorkRepository;
import com.managment.global_Trans_App.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DriverService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    DayWorkRepository dayWorkRepository;

    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }
    public List<Driver> getAllDriversByUser(User user) {
        return driverRepository.findByUser(user);
    }

    public Driver getDriverByUsername(String username) {
        return driverRepository.findByUser_Username(username);
    }

    public Driver findDriverById(Long id) {
        Optional<Driver> driver = driverRepository.findById(id);
        return driver.orElse(null);
    }
    public List<Driver> getAvailableDriversByDate(LocalDate date) {
        // Fetch all drivers
        List<Driver> allDrivers = driverRepository.findAll();

        // Filter drivers based on the selected date
        List<Driver> availableDrivers = allDrivers.stream()
                .filter(driver -> isDriverAvailable(driver, date))
                .collect(Collectors.toList());

        return availableDrivers;
    }
    private boolean isDriverAvailable(Driver driver, LocalDate date) {
        List<DayWork> driverDayWorks = dayWorkRepository.findByDriverId(driver.getId());
        return driverDayWorks.stream().noneMatch(dayWork -> dayWork.getDate().equals(date));
    }
    public Driver findById(Long driverId) {
        return driverRepository.findById(driverId).orElse(null);
    }


}