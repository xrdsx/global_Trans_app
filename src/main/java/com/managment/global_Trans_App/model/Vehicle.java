package com.managment.global_Trans_App.model;


import jakarta.persistence.*;

@Entity
@Table(name = "vehicle")

public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "mark")
    private String mark;
    @Column(name = "model")
    private String model;

    @Column(name = "year_of_production")
    private int yearOfProduction;
    @Column(name = "vin")
    private String vin;

    @Column(name = "registration_number")
    private String registrationNumber;

    public Vehicle() {
    }

    public Vehicle(String mark, String model, int yearOfProduction, String vin, String registrationNumber) {
        this.mark = mark;
        this.model = model;
        this.yearOfProduction = yearOfProduction;
        this.vin = vin;
        this.registrationNumber = registrationNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYearOfProduction() {
        return yearOfProduction;
    }

    public void setYearOfProduction(int yearOfProduction) {
        this.yearOfProduction = yearOfProduction;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }
}