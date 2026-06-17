package com.gler.assignment.exception;

import com.gler.assignment.dto.ErrorResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTextException.class)
    public ResponseEntity<String> handleInvalidTextException(
            InvalidTextException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    /**
     * Handles invalid forecast requests.
     */
    @ExceptionHandler(InvalidForecastRequestException.class)
    public ResponseEntity<String> handleInvalidForecastRequestException(
            InvalidForecastRequestException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    /**
     * Handles external API connectivity failures.
     */
    @ExceptionHandler(UpstreamApiException.class)
    public ResponseEntity<ErrorResponseDTO> handleUpstreamApiException(
            UpstreamApiException exception,
            HttpServletRequest request) {

        ErrorResponseDTO response =
                buildErrorResponse(
                        HttpStatus.BAD_GATEWAY,
                        "Upstream API Unreachable",
                        exception.getMessage(),
                        request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(response);
    }

    /**
     * Handles unexpected application errors.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(
            Exception exception,
            HttpServletRequest request) {

        ErrorResponseDTO response =
                buildErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Internal Server Error",
                        exception.getMessage(),
                        request.getRequestURI());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

    /**
     * Creates a standard error response.
     */
    private ErrorResponseDTO buildErrorResponse(
            HttpStatus status,
            String error,
            String message,
            String path) {

        ErrorResponseDTO response =
                new ErrorResponseDTO();

        response.setTimestamp(LocalDateTime.now());
        response.setStatus(status.value());
        response.setError(error);
        response.setMessage(message);
        response.setPath(path);

        return response;
    }
}