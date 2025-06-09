package com.example.app.domain.entity.journey;

import java.util.regex.Pattern;

import com.example.app.domain.exception.InvalidDomainException;

public record Station(String code) {

    private static final Pattern STATION_CODE_PATTERN = Pattern.compile("^[A-Z]{3}$");

    public Station {
        validateStation(code);
    }

    private static void validateStation(String code) {
        if (code == null || code.isBlank()) {
            throw new InvalidDomainException("Station code cannot be null or empty");
        }
        if (!STATION_CODE_PATTERN.matcher(code).matches()) {
            throw new InvalidDomainException("Station code must be exactly 3 uppercase letters: " + code);
        }
    }

    public static Station of(String code) {
        return new Station(code);
    }
}