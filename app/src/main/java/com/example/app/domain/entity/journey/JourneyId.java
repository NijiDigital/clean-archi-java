package com.example.app.domain.entity.journey;

import java.util.UUID;

import com.example.app.domain.exception.InvalidDomainException;

public record JourneyId(UUID value) {

    public JourneyId {
        if (value == null) {
            throw new InvalidDomainException("JourneyId cannot be null");
        }
    }

    public static JourneyId generate() {
        return new JourneyId(UUID.randomUUID());
    }

    public static JourneyId of(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidDomainException("JourneyId string cannot be null or blank");
        }
        try {
            return new JourneyId(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            throw new InvalidDomainException("Invalid JourneyId format: " + value);
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }

}