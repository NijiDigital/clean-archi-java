package fr.niji.example.trainresa.domain.entity.reservation;

import java.time.LocalDateTime;

import fr.niji.example.trainresa.domain.entity.journey.JourneyId;
import fr.niji.example.trainresa.domain.exception.InvalidDomainException;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Reservation {
    ReservationId id;
    JourneyId journeyId;
    Email passengerEmail;
    LocalDateTime createdAt;
    ReservationStatus status;

    public Reservation(ReservationId id, JourneyId journeyId, Email passengerEmail,
            LocalDateTime createdAt, ReservationStatus status) {
        if (id == null) {
            throw new InvalidDomainException("Reservation ID cannot be null");
        }
        if (journeyId == null) {
            throw new InvalidDomainException("Journey ID cannot be null");
        }
        if (passengerEmail == null) {
            throw new InvalidDomainException("Passenger email cannot be null");
        }
        if (createdAt == null) {
            throw new InvalidDomainException("Created at cannot be null");
        }
        if (status == null) {
            throw new InvalidDomainException("Reservation status cannot be null");
        }

        this.id = id;
        this.journeyId = journeyId;
        this.passengerEmail = passengerEmail;
        this.createdAt = createdAt;
        this.status = status;
    }

    // Constructeur de compatibilité pour ne pas casser le code existant
    public Reservation(ReservationId id, JourneyId journeyId, Email passengerEmail,
            LocalDateTime createdAt) {
        this(id, journeyId, passengerEmail, createdAt, ReservationStatus.CONFIRMED);
    }

    /**
     * Confirme la réservation
     * 
     * @return Une nouvelle réservation avec le statut CONFIRMED
     */
    public Reservation confirm() {
        return this.toBuilder()
                .status(ReservationStatus.CONFIRMED)
                .build();
    }

    /**
     * Annule la réservation
     * 
     * @return Une nouvelle réservation avec le statut CANCELLED
     */
    public Reservation cancel() {
        if (status == ReservationStatus.CANCELLED) {
            throw new InvalidDomainException("Reservation is already cancelled");
        }
        return this.toBuilder()
                .status(ReservationStatus.CANCELLED)
                .build();
    }

    /**
     * Vérifie si la réservation est active
     * 
     * @return true si la réservation est confirmée
     */
    public boolean isActive() {
        return status == ReservationStatus.CONFIRMED;
    }

    /**
     * Vérifie si la réservation est annulée
     * 
     * @return true si la réservation est annulée
     */
    public boolean isCancelled() {
        return status == ReservationStatus.CANCELLED;
    }
}