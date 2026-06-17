package com.gler.assignment.dto;

/**
 * Root response received from Open-Meteo API.
 */
public class OpenMeteoResponseDTO {

    private HourlyForecastDTO hourly;

    public HourlyForecastDTO getHourly() {
        return hourly;
    }

    public void setHourly(HourlyForecastDTO hourly) {
        this.hourly = hourly;
    }
}