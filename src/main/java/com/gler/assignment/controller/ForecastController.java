package com.gler.assignment.controller;

import com.gler.assignment.dto.ForecastRequestDTO;
import com.gler.assignment.dto.ForecastResponseDTO;
import com.gler.assignment.entity.ForecastEntity;
import com.gler.assignment.service.ForecastService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ForecastController {

    private final ForecastService forecastService;

    public ForecastController(
            ForecastService forecastService) {
        this.forecastService = forecastService;
    }

    /**
     * Retrieves forecast data, calculates the
     * maximum values for the requested date,
     * stores the result, and returns it.
     */
    @PostMapping("/forcast")
    public ForecastResponseDTO processForecast(
            @RequestBody ForecastRequestDTO request) {

        ForecastEntity forecastEntity =
                forecastService.processForecast(request);

        ForecastResponseDTO response =
                new ForecastResponseDTO();

        response.setDate(
                forecastEntity.getDate());

        response.setMaxTemperature(
                forecastEntity.getMaxTemperature());

        response.setMaxHumidity(
                forecastEntity.getMaxHumidity());

        response.setMaxWindSpeed(
                forecastEntity.getMaxWindSpeed());

        return response;
    }
}
