package com.gler.assignment.client;

import com.gler.assignment.dto.OpenMeteoResponseDTO;
import com.gler.assignment.exception.UpstreamApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

/**
 * Client responsible for communicating
 * with the Open-Meteo API.
 */
@Component
public class OpenMeteoClient {

    private final RestClient restClient;

    public OpenMeteoClient(
            @Value("${openmeteo.base-url}")
            String baseUrl) {

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .build();

    }

    /**
     * Retrieves forecast data from Open-Meteo.
     */
    public OpenMeteoResponseDTO getForecastData() {

        try {

            return restClient.get()
                    .uri("/v1/forecast" +
                            "?latitude=52.52" +
                            "&longitude=13.41" +
                            "&current=temperature_2m,wind_speed_10m" +
                            "&hourly=temperature_2m," +
                            "relative_humidity_2m," +
                            "wind_speed_10m")
                    .retrieve()
                    .body(OpenMeteoResponseDTO.class);

        } catch (RestClientException ex) {

            throw new UpstreamApiException(
                    "Connection to the upstream is unreachable");

        }
    }
}