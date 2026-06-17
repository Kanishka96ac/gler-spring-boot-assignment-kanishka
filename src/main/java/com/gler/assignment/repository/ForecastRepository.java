package com.gler.assignment.repository;

import com.gler.assignment.entity.ForecastEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for forecast data persistence.
 */
public interface ForecastRepository
        extends JpaRepository<ForecastEntity, Long> {
}