package com.managment.global_Trans_App.service;

import com.managment.global_Trans_App.model.Route;
import com.managment.global_Trans_App.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

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
}
