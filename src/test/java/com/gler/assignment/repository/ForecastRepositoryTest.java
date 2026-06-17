package com.gler.assignment.repository;

import com.gler.assignment.entity.ForecastEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ForecastRepositoryTest {

    @Autowired
    private ForecastRepository forecastRepository;

    /**
     * Verifies that a forecast record
     * can be saved successfully.
     */
    @Test
    void shouldSaveForecastEntity() {

        ForecastEntity entity =
                new ForecastEntity();

        entity.setDate(
                LocalDate.of(2026, 6, 18));

        entity.setMaxTemperature(29.8);
        entity.setMaxHumidity(75);
        entity.setMaxWindSpeed(13.3);

        ForecastEntity savedEntity =
                forecastRepository.save(entity);

        assertNotNull(savedEntity.getId());

        assertEquals(
                1,
                forecastRepository.findAll().size());
    }
}
