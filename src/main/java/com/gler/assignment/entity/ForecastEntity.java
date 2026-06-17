package com.gler.assignment.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "forecast")
public class ForecastEntity {

    // Primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Requested forecast date
    private LocalDate date;

    // Maximum temperature for the requested date
    private Double maxTemperature;

    // Maximum humidity for the requested date
    private Integer maxHumidity;

    // Maximum wind speed for the requested date
    private Double maxWindSpeed;

    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Integer getMaxHumidity() {
        return maxHumidity;
    }

    public void setMaxHumidity(Integer maxHumidity) {
        this.maxHumidity = maxHumidity;
    }

    public Double getMaxWindSpeed() {
        return maxWindSpeed;
    }

    public void setMaxWindSpeed(Double maxWindSpeed) {
        this.maxWindSpeed = maxWindSpeed;
    }
}