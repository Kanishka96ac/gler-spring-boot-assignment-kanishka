package com.gler.assignment.service;

import com.gler.assignment.client.OpenMeteoClient;
import com.gler.assignment.dto.ForecastRequestDTO;
import com.gler.assignment.dto.HourlyForecastDTO;
import com.gler.assignment.dto.OpenMeteoResponseDTO;
import com.gler.assignment.entity.ForecastEntity;
import com.gler.assignment.exception.InvalidForecastRequestException;
import com.gler.assignment.repository.ForecastRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ForecastServiceImpl implements ForecastService {

    private final OpenMeteoClient openMeteoClient;
    private final ForecastRepository forecastRepository;

    public ForecastServiceImpl(OpenMeteoClient openMeteoClient,
                               ForecastRepository forecastRepository) {
        this.openMeteoClient = openMeteoClient;
        this.forecastRepository = forecastRepository;
    }

    /**
     * Retrieves forecast data, extracts required values,
     * and stores the result in the database.
     */
    @Override
    public ForecastEntity processForecast(ForecastRequestDTO request) {

        validateRequest(request);

        OpenMeteoResponseDTO response = openMeteoClient.getForecastData();

        ForecastEntity forecastEntity = buildForecastEntity(request, response);

        return forecastRepository.save(forecastEntity);
    }

    /**
     * Validates mandatory request fields.
     */
    private void validateRequest(
            ForecastRequestDTO request) {

        if (request.getDate() == null
                || request.getAddTemprature() == null
                || request.getAddHumidity() == null
                || request.getAddWindSpeed() == null) {

            throw new InvalidForecastRequestException(
                    "All request parameters are mandatory");
        }
    }

    /**
     * Builds the entity that will be stored in the database.
     */
    private ForecastEntity buildForecastEntity(
            ForecastRequestDTO request,
            OpenMeteoResponseDTO response) {

        LocalDate requestedDate = request.getDate();

        Double maxTemperature =
                findMaxTemperature(response, requestedDate);

        Integer maxHumidity =
                findMaxHumidity(response, requestedDate);

        Double maxWindSpeed =
                findMaxWindSpeed(response, requestedDate);

        ForecastEntity forecastEntity =
                new ForecastEntity();

        forecastEntity.setDate(requestedDate);

        forecastEntity.setMaxTemperature(
                request.getAddTemprature()
                        ? maxTemperature
                        : null);

        forecastEntity.setMaxHumidity(
                request.getAddHumidity()
                        ? maxHumidity
                        : null);

        forecastEntity.setMaxWindSpeed(
                request.getAddWindSpeed()
                        ? maxWindSpeed
                        : null);

        return forecastEntity;
    }

    /**
     * Finds the maximum temperature for the requested date.
     */
    private Double findMaxTemperature(
            OpenMeteoResponseDTO response,
            LocalDate requestedDate) {

        HourlyForecastDTO hourly =
                response.getHourly();

        Double maxTemperature = null;

        for (int i = 0; i < hourly.getTime().size(); i++) {

            if (!isMatchingDate(
                    hourly.getTime().get(i),
                    requestedDate)) {
                continue;
            }

            Double temperature = hourly.getTemperatures().get(i);

            if (maxTemperature == null || temperature > maxTemperature) {
                maxTemperature = temperature;
            }
        }

        return maxTemperature;
    }

    /**
     * Finds the maximum humidity for the requested date.
     */
    private Integer findMaxHumidity(
            OpenMeteoResponseDTO response,
            LocalDate requestedDate) {

        HourlyForecastDTO hourly =
                response.getHourly();

        Integer maxHumidity = null;

        for (int i = 0; i < hourly.getTime().size(); i++) {

            if (!isMatchingDate(
                    hourly.getTime().get(i),
                    requestedDate)) {
                continue;
            }

            Integer humidity =
                    hourly.getHumidities().get(i);

            if (maxHumidity == null
                    || humidity > maxHumidity) {

                maxHumidity = humidity;
            }
        }

        return maxHumidity;
    }

    /**
     * Finds the maximum wind speed for the requested date.
     */
    private Double findMaxWindSpeed(
            OpenMeteoResponseDTO response,
            LocalDate requestedDate) {

        HourlyForecastDTO hourly =
                response.getHourly();

        Double maxWindSpeed = null;

        for (int i = 0; i < hourly.getTime().size(); i++) {

            if (!isMatchingDate(
                    hourly.getTime().get(i),
                    requestedDate)) {
                continue;
            }

            Double windSpeed =
                    hourly.getWindSpeeds().get(i);

            if (maxWindSpeed == null
                    || windSpeed > maxWindSpeed) {

                maxWindSpeed = windSpeed;
            }
        }

        return maxWindSpeed;
    }

    /**
     * Checks whether the forecast entry belongs
     * to the requested date.
     */
    private boolean isMatchingDate(
            String forecastTime,
            LocalDate requestedDate) {

        LocalDate forecastDate =
                LocalDate.parse(
                        forecastTime.substring(0, 10));

        return forecastDate.equals(requestedDate);
    }
}