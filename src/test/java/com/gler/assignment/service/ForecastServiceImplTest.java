package com.gler.assignment.service;

import com.gler.assignment.client.OpenMeteoClient;
import com.gler.assignment.dto.ForecastRequestDTO;
import com.gler.assignment.dto.HourlyForecastDTO;
import com.gler.assignment.dto.OpenMeteoResponseDTO;
import com.gler.assignment.entity.ForecastEntity;
import com.gler.assignment.exception.InvalidForecastRequestException;
import com.gler.assignment.repository.ForecastRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ForecastServiceImplTest {

    @Mock
    private OpenMeteoClient openMeteoClient;

    @Mock
    private ForecastRepository forecastRepository;

    @InjectMocks
    private ForecastServiceImpl forecastService;

    /**
     * Creates mock forecast data
     * used by multiple test cases.
     */
    private OpenMeteoResponseDTO buildResponse() {

        HourlyForecastDTO hourly =
                new HourlyForecastDTO();

        hourly.setTime(List.of(
                "2026-06-18T00:00",
                "2026-06-18T01:00",
                "2026-06-18T02:00"));

        hourly.setTemperatures(
                List.of(20.0, 29.8, 25.5));

        hourly.setHumidities(
                List.of(60, 75, 70));

        hourly.setWindSpeeds(
                List.of(10.0, 13.3, 11.2));

        OpenMeteoResponseDTO response =
                new OpenMeteoResponseDTO();

        response.setHourly(hourly);

        return response;
    }

    /**
     * Verifies successful forecast processing.
     */
    @Test
    void shouldProcessForecastSuccessfully() {

        ForecastRequestDTO request =
                new ForecastRequestDTO();

        request.setDate(
                LocalDate.of(2026, 6, 18));

        request.setAddTemprature(true);
        request.setAddHumidity(true);
        request.setAddWindSpeed(true);

        when(openMeteoClient.getForecastData())
                .thenReturn(buildResponse());

        when(forecastRepository.save(any()))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        ForecastEntity result =
                forecastService.processForecast(request);

        assertEquals(
                29.8,
                result.getMaxTemperature());

        assertEquals(
                75,
                result.getMaxHumidity());

        assertEquals(
                13.3,
                result.getMaxWindSpeed());
    }

    /**
     * Verifies temperature is stored as null
     * when addTemprature is false.
     */
    @Test
    void shouldStoreNullTemperatureWhenFlagIsFalse() {

        ForecastRequestDTO request =
                new ForecastRequestDTO();

        request.setDate(
                LocalDate.of(2026, 6, 18));

        request.setAddTemprature(false);
        request.setAddHumidity(true);
        request.setAddWindSpeed(true);

        when(openMeteoClient.getForecastData())
                .thenReturn(buildResponse());

        when(forecastRepository.save(any()))
                .thenAnswer(invocation ->
                        invocation.getArgument(0));

        ForecastEntity result =
                forecastService.processForecast(request);

        assertNull(result.getMaxTemperature());
    }

    /**
     * Verifies validation for missing date.
     */
    @Test
    void shouldThrowExceptionWhenDateIsNull() {

        ForecastRequestDTO request =
                new ForecastRequestDTO();

        request.setAddTemprature(true);
        request.setAddHumidity(true);
        request.setAddWindSpeed(true);

        assertThrows(
                InvalidForecastRequestException.class,
                () -> forecastService.processForecast(
                        request));
    }

    /**
     * Verifies validation for missing
     * temperature flag.
     */
    @Test
    void shouldThrowExceptionWhenTemperatureFlagIsNull() {

        ForecastRequestDTO request =
                new ForecastRequestDTO();

        request.setDate(
                LocalDate.of(2026, 6, 18));

        request.setAddHumidity(true);
        request.setAddWindSpeed(true);

        assertThrows(
                InvalidForecastRequestException.class,
                () -> forecastService.processForecast(
                        request));
    }

    /**
     * Verifies validation for missing
     * humidity flag.
     */
    @Test
    void shouldThrowExceptionWhenHumidityFlagIsNull() {

        ForecastRequestDTO request =
                new ForecastRequestDTO();

        request.setDate(
                LocalDate.of(2026, 6, 18));

        request.setAddTemprature(true);
        request.setAddWindSpeed(true);

        assertThrows(
                InvalidForecastRequestException.class,
                () -> forecastService.processForecast(
                        request));
    }

    /**
     * Verifies validation for missing
     * wind speed flag.
     */
    @Test
    void shouldThrowExceptionWhenWindSpeedFlagIsNull() {

        ForecastRequestDTO request =
                new ForecastRequestDTO();

        request.setDate(
                LocalDate.of(2026, 6, 18));

        request.setAddTemprature(true);
        request.setAddHumidity(true);

        assertThrows(
                InvalidForecastRequestException.class,
                () -> forecastService.processForecast(
                        request));
    }

}