package com.example.app.domain.entity.reservation;

/**
 * Statut d'une réservation
 */
public enum ReservationStatus {
    /**
     * Réservation confirmée et active
     */
    CONFIRMED,

    /**
     * Réservation annulée
     */
    CANCELLED,

    /**
     * Réservation en attente de confirmation
     */
    PENDING
}