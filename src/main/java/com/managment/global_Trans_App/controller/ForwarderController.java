package com.managment.global_Trans_App.controller;

import com.managment.global_Trans_App.model.Forwarder;
import com.managment.global_Trans_App.model.RoleType;
import com.managment.global_Trans_App.model.User;
import com.managment.global_Trans_App.repository.ForwarderRepository;
import com.managment.global_Trans_App.repository.UserRepository;
import com.managment.global_Trans_App.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/forwarder-management")
public class ForwarderController {

    private final ForwarderRepository forwarderRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public ForwarderController(ForwarderRepository forwarderRepository, UserRepository userRepository, UserService userService) {
        this.forwarderRepository = forwarderRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping
    public String forwarderList(Model model) {
        List<Forwarder> forwarders = forwarderRepository.findAllByUser_Role(RoleType.FORWARDER);
        model.addAttribute("forwarders", forwarders);
        return "forwarder-management";
    }

    @GetMapping("/api/forwarders")
    @ResponseBody
    public List<Forwarder> getForwarders() {
        return forwarderRepository.findAll();
    }

    @GetMapping("/add")
    public String showAddForwarderForm(Model model) {
        model.addAttribute("forwarder", new Forwarder());
        return "addForwarder";
    }

    @PostMapping("/add")
    public String addForwarder(@ModelAttribute("forwarder") Forwarder forwarder,
                               @RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model) {
        if (!userService.isUsernameUnique(username)) {
            model.addAttribute("usernameError", "Username already exists.");
            return "addForwarder";
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(RoleType.FORWARDER);
        userRepository.save(user);

        forwarder.setUser(user);
        forwarderRepository.save(forwarder);
        model.addAttribute("success", true);
        return "redirect:/forwarder-management";
    }

    @GetMapping("/edit/{id}")
    public String showEditForwarderForm(@PathVariable("id") int id, Model model) {
        Forwarder forwarder = forwarderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid forwarder Id:" + id));
        model.addAttribute("forwarder", forwarder);
        return "editForwarder";
    }

    @PostMapping("/edit/{id}")
    public String updateForwarder(@PathVariable("id") int id, @ModelAttribute("forwarder") Forwarder forwarder) {
        forwarderRepository.save(forwarder);
        return "redirect:/forwarder-management";
    }

    @GetMapping("/delete/{id}")
    public String deleteForwarder(@PathVariable("id") int id) {
        forwarderRepository.deleteById(id);
        return "redirect:/forwarder-management";
    }

}