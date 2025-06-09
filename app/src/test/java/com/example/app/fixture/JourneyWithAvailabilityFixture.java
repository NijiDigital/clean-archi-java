package com.example.app.fixture;

import java.time.LocalDateTime;

import com.example.app.domain.entity.journey.JourneyId;
import com.example.app.domain.query.view.JourneyWithAvailability;

public class JourneyWithAvailabilityFixture {

    public static JourneyWithAvailability.JourneyWithAvailabilityBuilder defaultBuilder() {
        return JourneyWithAvailability.builder()
                .id(JourneyId.generate().toString())
                .trainNumber("TGV001")
                .departureStationCode("PAR")
                .arrivalStationCode("LYO")
                .departureTime(LocalDateTime.now().plusHours(1))
                .arrivalTime(LocalDateTime.now().plusHours(3))
                .capacity(50)
                .availableSeats(40); // Par défaut, 10 places réservées
    }

    public static JourneyWithAvailability defaultJourneyWithAvailability() {
        return defaultBuilder().build();
    }

    public static JourneyWithAvailability journeyWithAvailability(String id, String trainNumber,
            String departureCode, String arrivalCode, int capacity, int availableSeats) {
        return defaultBuilder()
                .id(id)
                .trainNumber(trainNumber)
                .departureStationCode(departureCode)
                .arrivalStationCode(arrivalCode)
                .capacity(capacity)
                .availableSeats(availableSeats)
                .build();
    }

    public static JourneyWithAvailability journeyWithCapacityAndAvailability(int capacity, int availableSeats) {
        return defaultBuilder()
                .capacity(capacity)
                .availableSeats(availableSeats)
                .build();
    }

    public static JourneyWithAvailability fullJourney() {
        return defaultBuilder()
                .availableSeats(0)
                .build();
    }

    public static JourneyWithAvailability emptyJourney() {
        var capacity = 50;
        return defaultBuilder()
                .capacity(capacity)
                .availableSeats(capacity)
                .build();
    }
}