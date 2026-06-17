package com.gler.assignment.integration;

import com.gler.assignment.client.OpenMeteoClient;
import com.gler.assignment.dto.OpenMeteoResponseDTO;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ForecastWireMockIntegrationTest {

    private static WireMockServer wireMockServer;

    @Autowired
    private OpenMeteoClient openMeteoClient;

    @BeforeAll
    static void startWireMock() {

        wireMockServer =
                new WireMockServer(8089);

        wireMockServer.start();

        configureFor(
                "localhost",
                8089);
    }

    @AfterAll
    static void stopWireMock() {

        wireMockServer.stop();
    }

    /**
     * Verifies successful communication
     * with the mocked weather API.
     */
    @Test
    void shouldReturnForecastFromWireMock() {

        stubFor(get(urlPathEqualTo("/v1/forecast"))
                .willReturn(
                        aResponse()
                                .withStatus(200)
                                .withHeader(
                                        "Content-Type",
                                        "application/json")
                                .withBody("""
                                        {
                                          "hourly": {
                                            "time": [
                                              "2026-06-18T00:00",
                                              "2026-06-18T01:00"
                                            ],
                                            "temperature_2m": [
                                              20.0,
                                              29.8
                                            ],
                                            "relative_humidity_2m": [
                                              60,
                                              75
                                            ],
                                            "wind_speed_10m": [
                                              10.0,
                                              13.3
                                            ]
                                          }
                                        }
                                        """)));

        OpenMeteoResponseDTO response =
                openMeteoClient.getForecastData();

        assertNotNull(response);
        assertNotNull(response.getHourly());

        verify(getRequestedFor(
                urlPathEqualTo("/v1/forecast")));
    }

    /**
     * Verifies upstream API failure handling.
     */
    @Test
    void shouldHandleUpstreamApiFailure() {

        stubFor(get(urlPathEqualTo("/v1/forecast"))
                .willReturn(
                        aResponse()
                                .withStatus(500)));

        assertThrows(
                Exception.class,
                () -> openMeteoClient.getForecastData());
    }

}