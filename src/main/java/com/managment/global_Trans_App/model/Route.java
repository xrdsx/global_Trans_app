package com.managment.global_Trans_App.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nameRoute;

    @Column(nullable = true)
    private Integer forwarder_km;

    private String map_url;
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;
    @OneToMany(mappedBy = "route")
    private List<DayWork> dayWorks;

    public Route() {
        // konstruktor domyślny
    }

    public Route(int id, String nameRoute, Integer forwarder_km, String map_url) {
        this.id = id;
        this.nameRoute = nameRoute;
        this.forwarder_km = forwarder_km;
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

    public Integer getForwarder_km() {
        return forwarder_km;
    }

    public void setForwarder_km(Integer forwarder_km) {
        this.forwarder_km = forwarder_km;
    }

    public String getMap_url() {
        return map_url;
    }

    public void setMap_url(String map_url) {
        this.map_url = map_url;
    }

    @Override
    public String toString() {
        return "Route [id=" + id + ", nameRoute=" + nameRoute + ", forwarder_km=" + forwarder_km + ", map_url=" + map_url + "]";
    }
}