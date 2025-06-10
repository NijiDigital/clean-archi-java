package fr.niji.example.trainresa.fixture;

import java.time.LocalDateTime;

import fr.niji.example.trainresa.domain.entity.journey.Journey;
import fr.niji.example.trainresa.domain.entity.journey.JourneyId;
import fr.niji.example.trainresa.domain.entity.journey.Station;
import fr.niji.example.trainresa.domain.entity.journey.TrainNumber;

public class JourneyFixture {

    public static Journey.JourneyBuilder defaultBuilder() {
        return Journey.builder()
                .id(JourneyId.generate())
                .trainNumber(TrainNumber.of("TGV001"))
                .departureStation(Station.of("PAR"))
                .arrivalStation(Station.of("LYO"))
                .departureTime(LocalDateTime.now().plusHours(1))
                .arrivalTime(LocalDateTime.now().plusHours(3))
                .capacity(50);
    }

    public static Journey defaultJourney() {
        return defaultBuilder().build();
    }

    public static Journey journeyWithCapacity(int capacity) {
        return defaultBuilder()
                .capacity(capacity)
                .build();
    }

    public static Journey journeyWithCapacity(JourneyId id, int capacity) {
        return defaultBuilder()
                .id(id)
                .capacity(capacity)
                .build();
    }

    public static Journey journeyWithStations(String departureCode, String arrivalCode) {
        return defaultBuilder()
                .departureStation(Station.of(departureCode))
                .arrivalStation(Station.of(arrivalCode))
                .build();
    }

    public static Journey journeyWithTrainNumber(String trainNumber) {
        return defaultBuilder()
                .trainNumber(TrainNumber.of(trainNumber))
                .build();
    }

    public static Journey journeyWithDetails(JourneyId id, String trainNumber,
            String departureCode, String arrivalCode, int capacity) {
        return defaultBuilder()
                .id(id)
                .trainNumber(TrainNumber.of(trainNumber))
                .departureStation(Station.of(departureCode))
                .arrivalStation(Station.of(arrivalCode))
                .capacity(capacity)
                .build();
    }
}