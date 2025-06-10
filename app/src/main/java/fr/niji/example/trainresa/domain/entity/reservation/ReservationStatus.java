package fr.niji.example.trainresa.domain.entity.reservation;

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