package com.example.app.domain.exception;

import com.example.app.domain.entity.journey.JourneyId;

public class NoAvailableSeatsException extends RuntimeException {

    public NoAvailableSeatsException(JourneyId journeyId) {
        super("No available seats for journey with ID: " + journeyId);
    }
}