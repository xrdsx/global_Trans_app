package com.managment.global_Trans_App.service;

import com.managment.global_Trans_App.model.DayWork;
import com.managment.global_Trans_App.model.Route;
import com.managment.global_Trans_App.repository.DayWorkRepository;
import com.managment.global_Trans_App.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    DayWorkRepository dayWorkRepository;

    public List<Route> getAllRoutes() {
        return routeRepository.findAll();
    }

    public Optional<Route> getRouteById(int id) {
        return routeRepository.findById(id);
    }

    public void saveRoute(Route route) {
        routeRepository.save(route);
    }

    public void deleteRoute(int id) {
        routeRepository.deleteById(id);
    }

    public Route findRouteById(int id) {
        return routeRepository.findById(id).orElse(null);
    }

    public List<Route> getAvailableRoutesByDate(LocalDate date) {
        // Fetch all routes
        List<Route> allRoutes = routeRepository.findAll();

        // Filter routes based on the selected date
        List<Route> availableRoutes = allRoutes.stream()
                .filter(route -> isRouteAvailable(route, date))
                .collect(Collectors.toList());

        return availableRoutes;
    }
    private boolean isRouteAvailable(Route route, LocalDate date) {
        List<DayWork> routeDayWorks = dayWorkRepository.findByRouteId(route.getId());
        return routeDayWorks.stream().noneMatch(dayWork -> dayWork.getDate().equals(date));
    }
}
