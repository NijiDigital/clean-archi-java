package fr.niji.example.trainresa.domain.entity.reservation;

import java.util.UUID;

import fr.niji.example.trainresa.domain.exception.InvalidDomainException;

public record ReservationId(UUID value) {

    public ReservationId {
        if (value == null) {
            throw new InvalidDomainException("ReservationId cannot be null");
        }
    }

    public static ReservationId generate() {
        return new ReservationId(UUID.randomUUID());
    }

    public static ReservationId of(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidDomainException("ReservationId string cannot be null or blank");
        }
        try {
            return new ReservationId(UUID.fromString(value));
        } catch (IllegalArgumentException e) {
            throw new InvalidDomainException("Invalid ReservationId format: " + value);
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }

}