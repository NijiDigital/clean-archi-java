package fr.niji.example.trainresa.domain.exception;

import fr.niji.example.trainresa.domain.entity.journey.JourneyId;

public class JourneyNotFoundException extends RuntimeException {

    public JourneyNotFoundException(JourneyId journeyId) {
        super("Journey not found with ID: " + journeyId);
    }
}