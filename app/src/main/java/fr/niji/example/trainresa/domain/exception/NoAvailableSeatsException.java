package fr.niji.example.trainresa.domain.exception;

import fr.niji.example.trainresa.domain.entity.journey.JourneyId;

public class NoAvailableSeatsException extends RuntimeException {

    public NoAvailableSeatsException(JourneyId journeyId) {
        super("No available seats for journey with ID: " + journeyId);
    }
}