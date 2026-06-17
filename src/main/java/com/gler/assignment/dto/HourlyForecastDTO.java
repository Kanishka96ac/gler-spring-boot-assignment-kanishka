package com.gler.assignment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Hourly forecast values returned by Open-Meteo.
 */
public class HourlyForecastDTO {

    private List<String> time;

    @JsonProperty("temperature_2m")
    private List<Double> temperatures;

    @JsonProperty("relative_humidity_2m")
    private List<Integer> humidities;

    @JsonProperty("wind_speed_10m")
    private List<Double> windSpeeds;

    public List<String> getTime() {
        return time;
    }

    public void setTime(List<String> time) {
        this.time = time;
    }

    public List<Double> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(List<Double> temperatures) {
        this.temperatures = temperatures;
    }

    public List<Integer> getHumidities() {
        return humidities;
    }

    public void setHumidities(List<Integer> humidities) {
        this.humidities = humidities;
    }

    public List<Double> getWindSpeeds() {
        return windSpeeds;
    }

    public void setWindSpeeds(List<Double> windSpeeds) {
        this.windSpeeds = windSpeeds;
    }
}