package com.example.app.domain.repository;

import com.example.app.domain.entity.journey.JourneyId;
import com.example.app.domain.entity.reservation.Reservation;
import com.example.app.domain.entity.reservation.ReservationId;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    boolean existsById(ReservationId reservationId);

    /**
     * Compte le nombre de réservations actives (confirmées) pour un trajet donné
     * 
     * @param journeyId L'identifiant du trajet
     * @return Le nombre de réservations actives
     */
    int countActiveReservationsByJourneyId(JourneyId journeyId);
}