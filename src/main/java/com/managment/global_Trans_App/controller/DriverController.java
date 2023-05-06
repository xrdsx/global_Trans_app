package com.managment.global_Trans_App.controller;

import com.managment.global_Trans_App.model.Driver;
import com.managment.global_Trans_App.model.RoleType;
import com.managment.global_Trans_App.model.User;
import com.managment.global_Trans_App.repository.DriverRepository;
import com.managment.global_Trans_App.repository.UserRepository;
import com.managment.global_Trans_App.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/driver-management")
public class DriverController {

    private final DriverRepository driverRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public DriverController(DriverRepository driverRepository, UserRepository userRepository, UserService userService) {
        this.driverRepository = driverRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @GetMapping
    public String driverList(Model model) {
        List<Driver> drivers = driverRepository.findAllByUser_Role(RoleType.DRIVER);
        model.addAttribute("drivers", drivers);
        return "driver-management";
    }
    @GetMapping("/api/drivers")
    @ResponseBody
    public List<Driver> getDrivers() {
        return driverRepository.findAll();
    }


    @GetMapping("/add")
    public String showAddDriverForm(Model model) {
        model.addAttribute("driver", new Driver());
        return "addDriver";
    }


    @PostMapping("/add")
    public String addDriver(@ModelAttribute("driver") Driver driver,
                            @RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Model model) {
        if (!userService.isUsernameUnique(username)) {
            model.addAttribute("usernameError", "Username already exists.");
            return "addDriver";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(RoleType.DRIVER);
        userRepository.save(user);

        driver.setUser(user);
        driverRepository.save(driver);
        model.addAttribute("success", true);
        return "redirect:/driver-management";
    }

    @GetMapping("/edit/{id}")
    public String showEditDriverForm(@PathVariable("id") Long id, Model model) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid driver Id:" + id));
        model.addAttribute("driver", driver);
        return "editDriver";
    }

    @PostMapping("/edit/{id}")
    public String updateDriver(@PathVariable("id") Long id, @ModelAttribute("driver") Driver driver) {
        driverRepository.save(driver);
        return "redirect:/driver-management";
    }

    @GetMapping("/delete/{id}")
    public String deleteDriver(@PathVariable("id") Long id) {
        driverRepository.deleteById(id);
        return "redirect:/driver-management";
    }

}