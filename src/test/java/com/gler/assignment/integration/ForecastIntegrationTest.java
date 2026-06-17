package com.gler.assignment.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gler.assignment.dto.ForecastRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ForecastIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Verifies the complete flow:
     * Controller -> Service -> Repository.
     */
    @Test
    void shouldProcessForecastEndToEnd()
            throws Exception {

        ForecastRequestDTO request =
                new ForecastRequestDTO();

        request.setDate(
                LocalDate.of(2026, 6, 18));

        request.setAddTemprature(true);
        request.setAddHumidity(true);
        request.setAddWindSpeed(true);

        mockMvc.perform(
                        post("/forcast")
                                .contentType("application/json")
                                .content(
                                        objectMapper.writeValueAsString(
                                                request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.date")
                        .value("2026-06-18"));
    }

    /**
     * Verifies validation when date is missing.
     */
    @Test
    void shouldReturnBadRequestWhenDateMissing()
            throws Exception {

        String requestBody = """
            {
              "addTemprature": true,
              "addHumidity": true,
              "addWindSpeed": true
            }
            """;

        mockMvc.perform(
                        post("/forcast")
                                .contentType("application/json")
                                .content(requestBody))
                .andExpect(status().isBadRequest());
    }
}