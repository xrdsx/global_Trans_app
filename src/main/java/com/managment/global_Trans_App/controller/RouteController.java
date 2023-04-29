package com.managment.global_Trans_App.controller;

import com.managment.global_Trans_App.model.Route;
import com.managment.global_Trans_App.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/route-management")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @GetMapping
    public String routeList(Model model) {
        List<Route> routes = routeService.getAllRoutes();
        model.addAttribute("routes", routes);
        return "route-management";
    }

    @GetMapping("/add")
    public String addRoute(Model model) {
        Route route = new Route();
        model.addAttribute("route", route);
        return "add-route";
    }

    @PostMapping("/save")
    public String saveRoute(@ModelAttribute("route") Route route) {
        routeService.saveRoute(route);
        return "redirect:/route-management";
    }

    @GetMapping("/edit/{id}")
    public String editRoute(Model model, @PathVariable(value = "id") int id) {
        Optional<Route> routeOptional = routeService.getRouteById(id);
        Route route = routeOptional.orElseThrow(() -> new IllegalArgumentException("Invalid route Id:" + id));
        model.addAttribute("route", route);
        return "edit-route";
    }

    @PostMapping("/update/{id}")
    public String updateRoute(@ModelAttribute("route") Route route, @PathVariable(value = "id") int id) {
        Route existingRoute = routeService.getRouteById(id).orElseThrow(() -> new IllegalArgumentException("Invalid route Id:" + id));
        existingRoute.setNameRoute(route.getNameRoute());
        existingRoute.setMap_url(route.getMap_url());

        routeService.saveRoute(existingRoute);
        return "redirect:/route-management";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoute(@PathVariable(value = "id") int id) {
        routeService.deleteRoute(id);
        return "redirect:/route-management";
    }
}