package com.example.app.domain.usecase;

import java.time.LocalDateTime;

import com.example.app.domain.entity.journey.JourneyId;
import com.example.app.domain.entity.reservation.Email;
import com.example.app.domain.entity.reservation.Reservation;
import com.example.app.domain.entity.reservation.ReservationId;
import com.example.app.domain.exception.JourneyNotFoundException;
import com.example.app.domain.exception.NoAvailableSeatsException;
import com.example.app.domain.repository.JourneyRepository;
import com.example.app.domain.repository.ReservationRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ReserveJourneyUseCase {

    private final JourneyRepository journeyRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public ReserveJourneyResult execute(ReserveJourneyRequest request) {
        // Récupérer le trajet
        var journey = journeyRepository.findById(request.journeyId())
                .orElseThrow(() -> new JourneyNotFoundException(request.journeyId()));

        // Compter les réservations actives existantes pour ce trajet
        var activeReservations = reservationRepository.countActiveReservationsByJourneyId(request.journeyId());

        // Vérifier la disponibilité en fonction de la capacité et des réservations
        // existantes
        if (!journey.hasAvailableSeats(activeReservations)) {
            throw new NoAvailableSeatsException(request.journeyId());
        }

        // Créer la réservation
        var reservation = new Reservation(
                ReservationId.generate(),
                request.journeyId(),
                Email.of(request.passengerEmail()),
                LocalDateTime.now());

        // Sauvegarder la réservation
        reservationRepository.save(reservation);

        // Plus besoin de modifier le trajet car la gestion des places se fait par
        // comptage

        return new ReserveJourneyResult(reservation);
    }

    public record ReserveJourneyRequest(JourneyId journeyId, String passengerEmail) {
    }

    public record ReserveJourneyResult(Reservation reservation) {
    }
}