package com.gler.assignment.dto;

import java.time.LocalDate;

public class ForecastRequestDTO {

    // Date used to filter forecast data
    private LocalDate date;

    // Include maximum temperature in the stored record
    private Boolean addTemprature;

    // Include maximum humidity in the stored record
    private Boolean addHumidity;

    // Include maximum wind speed in the stored record
    private Boolean addWindSpeed;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getAddTemprature() {
        return addTemprature;
    }

    public void setAddTemprature(Boolean addTemprature) {
        this.addTemprature = addTemprature;
    }

    public Boolean getAddHumidity() {
        return addHumidity;
    }

    public void setAddHumidity(Boolean addHumidity) {
        this.addHumidity = addHumidity;
    }

    public Boolean getAddWindSpeed() {
        return addWindSpeed;
    }

    public void setAddWindSpeed(Boolean addWindSpeed) {
        this.addWindSpeed = addWindSpeed;
    }
}