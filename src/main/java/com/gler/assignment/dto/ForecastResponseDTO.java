package com.gler.assignment.dto;

import java.time.LocalDate;

public class ForecastResponseDTO {

    // Forecast date
    private LocalDate date;

    // Maximum temperature for the date
    private Double maxTemperature;

    // Maximum humidity for the date
    private Integer maxHumidity;

    // Maximum wind speed for the date
    private Double maxWindSpeed;

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