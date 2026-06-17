package com.gler.assignment.service;

import com.gler.assignment.dto.ForecastRequestDTO;
import com.gler.assignment.entity.ForecastEntity;

/**
 * Forecast business operations.
 */
public interface ForecastService {

    ForecastEntity processForecast(ForecastRequestDTO request);
}