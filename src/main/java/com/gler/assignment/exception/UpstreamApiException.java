package com.gler.assignment.exception;

/**
 * Thrown when the external weather API
 * cannot be reached.
 */
public class UpstreamApiException
        extends RuntimeException {

    public UpstreamApiException(String message) {
        super(message);
    }
}