package fr.niji.example.trainresa.infrastructure.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import fr.niji.example.trainresa.domain.exception.InvalidDomainException;
import fr.niji.example.trainresa.domain.exception.JourneyNotFoundException;
import fr.niji.example.trainresa.domain.exception.NoAvailableSeatsException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidDomainException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDomainException(InvalidDomainException e) {
        log.warn("Invalid domain exception: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("INVALID_INPUT", e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDomainException(MethodArgumentNotValidException e) {
        log.warn("Invalid domain exception: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("INVALID_INPUT", e.getBindingResult().getFieldError().getDefaultMessage()));
    }

    @ExceptionHandler(JourneyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleJourneyNotFoundException(JourneyNotFoundException e) {
        log.warn("Journey not found: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("JOURNEY_NOT_FOUND", e.getMessage()));
    }

    @ExceptionHandler(NoAvailableSeatsException.class)
    public ResponseEntity<ErrorResponse> handleNoAvailableSeatsException(NoAvailableSeatsException e) {
        log.warn("No available seats: {}", e.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("NO_AVAILABLE_SEATS", e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        log.error("Unexpected error occurred", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("INTERNAL_ERROR", "An unexpected error occurred"));
    }

    public record ErrorResponse(String code, String message) {
    }
}