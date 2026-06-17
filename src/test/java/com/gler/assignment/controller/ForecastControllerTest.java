package com.gler.assignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gler.assignment.dto.ForecastRequestDTO;
import com.gler.assignment.entity.ForecastEntity;
import com.gler.assignment.service.ForecastService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ForecastController.class)
public class ForecastControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ForecastService forecastService;

    /**
     * Verifies successful forecast processing.
     */
    @Test
    void shouldReturnForecastResponse()
            throws Exception {

        ForecastRequestDTO request =
                new ForecastRequestDTO();

        request.setDate(
                LocalDate.of(2026, 6, 18));

        request.setAddTemprature(true);
        request.setAddHumidity(true);
        request.setAddWindSpeed(true);

        ForecastEntity entity =
                new ForecastEntity();

        entity.setDate(
                LocalDate.of(2026, 6, 18));

        entity.setMaxTemperature(29.8);
        entity.setMaxHumidity(75);
        entity.setMaxWindSpeed(13.3);

        when(forecastService.processForecast(any()))
                .thenReturn(entity);

        mockMvc.perform(
                        post("/forcast")
                                .contentType("application/json")
                                .content(
                                        objectMapper.writeValueAsString(
                                                request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date")
                        .value("2026-06-18"))
                .andExpect(jsonPath("$.maxTemperature")
                        .value(29.8))
                .andExpect(jsonPath("$.maxHumidity")
                        .value(75))
                .andExpect(jsonPath("$.maxWindSpeed")
                        .value(13.3));
    }

}