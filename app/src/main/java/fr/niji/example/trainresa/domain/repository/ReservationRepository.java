package fr.niji.example.trainresa.domain.repository;

import fr.niji.example.trainresa.domain.entity.journey.JourneyId;
import fr.niji.example.trainresa.domain.entity.reservation.Reservation;
import fr.niji.example.trainresa.domain.entity.reservation.ReservationId;

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