package com.example.app.domain.entity.journey;

import java.time.LocalDateTime;

import com.example.app.domain.exception.InvalidDomainException;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Journey {
    JourneyId id;
    TrainNumber trainNumber;
    Station departureStation;
    Station arrivalStation;
    LocalDateTime departureTime;
    LocalDateTime arrivalTime;
    int capacity;

    public Journey(JourneyId id, TrainNumber trainNumber, Station departureStation,
            Station arrivalStation, LocalDateTime departureTime,
            LocalDateTime arrivalTime, int capacity) {
        if (id == null) {
            throw new InvalidDomainException("Journey ID cannot be null");
        }
        if (trainNumber == null) {
            throw new InvalidDomainException("Train number cannot be null");
        }
        if (departureStation == null) {
            throw new InvalidDomainException("Departure station cannot be null");
        }
        if (arrivalStation == null) {
            throw new InvalidDomainException("Arrival station cannot be null");
        }
        if (departureTime == null) {
            throw new InvalidDomainException("Departure time cannot be null");
        }
        if (arrivalTime == null) {
            throw new InvalidDomainException("Arrival time cannot be null");
        }
        if (departureTime.isAfter(arrivalTime) || departureTime.isEqual(arrivalTime)) {
            throw new InvalidDomainException("Departure time must be before arrival time");
        }
        if (capacity < 0) {
            throw new InvalidDomainException("Capacity cannot be negative");
        }

        this.id = id;
        this.trainNumber = trainNumber;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.capacity = capacity;
    }

    /**
     * Vérifie s'il y a des places disponibles
     * 
     * @param reservedSeats Le nombre de places déjà réservées
     * @return true s'il reste des places disponibles
     */
    public boolean hasAvailableSeats(int reservedSeats) {
        return capacity > reservedSeats;
    }

    /**
     * Calcule le nombre de places disponibles
     * 
     * @param reservedSeats Le nombre de places déjà réservées
     * @return Le nombre de places disponibles
     */
    public int getAvailableSeats(int reservedSeats) {
        return Math.max(0, capacity - reservedSeats);
    }

    /**
     * Met à jour les horaires du trajet
     * 
     * @param newDepartureTime Nouvelle heure de départ
     * @param newArrivalTime   Nouvelle heure d'arrivée
     * @return Un nouveau Journey avec les horaires mis à jour
     */
    public Journey reschedule(LocalDateTime newDepartureTime, LocalDateTime newArrivalTime) {
        if (newDepartureTime == null) {
            throw new InvalidDomainException("Departure time cannot be null");
        }
        if (newArrivalTime == null) {
            throw new InvalidDomainException("Arrival time cannot be null");
        }
        if (newDepartureTime.isAfter(newArrivalTime) || newDepartureTime.isEqual(newArrivalTime)) {
            throw new InvalidDomainException("Departure time must be before arrival time");
        }

        return this.toBuilder()
                .departureTime(newDepartureTime)
                .arrivalTime(newArrivalTime)
                .build();
    }
}