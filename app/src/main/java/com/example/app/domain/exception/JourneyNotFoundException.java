package com.example.app.domain.exception;

import com.example.app.domain.entity.journey.JourneyId;

public class JourneyNotFoundException extends RuntimeException {

    public JourneyNotFoundException(JourneyId journeyId) {
        super("Journey not found with ID: " + journeyId);
    }
}