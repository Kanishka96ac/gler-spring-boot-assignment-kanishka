package com.gler.assignment.exception;

/**
 * Thrown when the forecast request is invalid.
 */
public class InvalidForecastRequestException
        extends RuntimeException {

    public InvalidForecastRequestException(String message) {
        super(message);
    }
}