package com.managment.global_Trans_App.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nameRoute;
    private String map_url;

    public Route() {
        // konstruktor domyślny
    }

    public Route(int id, String nameRoute, String map_url) {
        this.id = id;
        this.nameRoute = nameRoute;
        this.map_url = map_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameRoute() {
        return nameRoute;
    }

    public void setNameRoute(String nameRoute) {
        this.nameRoute = nameRoute;
    }

    public String getMap_url() {
        return map_url;
    }

    public void setMap_url(String map_url) {
        this.map_url = map_url;
    }

    @Override
    public String toString() {
        return "Route [id=" + id + ", nameRoute=" + nameRoute + ", map_url=" + map_url + "]";
    }
}