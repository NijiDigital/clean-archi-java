package fr.niji.example.trainresa.fixture;

import java.util.HashMap;
import java.util.Map;

import fr.niji.example.trainresa.domain.entity.journey.JourneyId;
import fr.niji.example.trainresa.domain.entity.reservation.Reservation;
import fr.niji.example.trainresa.domain.entity.reservation.ReservationId;
import fr.niji.example.trainresa.domain.entity.reservation.ReservationStatus;
import fr.niji.example.trainresa.domain.repository.ReservationRepository;

public class ReservationInMemoryRepository implements ReservationRepository {

    private final Map<ReservationId, Reservation> reservations = new HashMap<>();

    @Override
    public Reservation save(Reservation reservation) {
        reservations.put(reservation.getId(), reservation);
        return reservation;
    }

    @Override
    public boolean existsById(ReservationId reservationId) {
        return reservations.containsKey(reservationId);
    }

    @Override
    public int countActiveReservationsByJourneyId(JourneyId journeyId) {
        return (int) reservations.values().stream()
                .filter(reservation -> reservation.getJourneyId().equals(journeyId))
                .filter(reservation -> reservation.getStatus() == ReservationStatus.CONFIRMED)
                .count();
    }

    // Méthodes utilitaires pour les tests
    public void clear() {
        reservations.clear();
    }

    public void addReservation(Reservation reservation) {
        reservations.put(reservation.getId(), reservation);
    }

    public boolean contains(ReservationId reservationId) {
        return reservations.containsKey(reservationId);
    }

    public int size() {
        return reservations.size();
    }

    public long countByJourneyId(JourneyId journeyId) {
        return reservations.values().stream()
                .filter(reservation -> reservation.getJourneyId().equals(journeyId))
                .count();
    }

    public long countByJourneyIdAndStatus(JourneyId journeyId, ReservationStatus status) {
        return reservations.values().stream()
                .filter(reservation -> reservation.getJourneyId().equals(journeyId))
                .filter(reservation -> reservation.getStatus() == status)
                .count();
    }
}