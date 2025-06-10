package fr.niji.example.trainresa.domain.query.view;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Value;

/**
 * View Model représentant un Journey avec ses places disponibles
 * Appartient au domaine - représente une vue métier
 */
@Value
@Builder(toBuilder = true)
public class JourneyWithAvailability {
    String id;
    String trainNumber;
    String departureStationCode;
    String arrivalStationCode;
    LocalDateTime departureTime;
    LocalDateTime arrivalTime;
    int capacity;
    int availableSeats;
}