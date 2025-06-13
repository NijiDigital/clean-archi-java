package fr.niji.example.trainresa.domain.usecase;

import java.time.LocalDateTime;

import fr.niji.example.trainresa.domain.entity.journey.JourneyId;
import fr.niji.example.trainresa.domain.entity.reservation.Email;
import fr.niji.example.trainresa.domain.entity.reservation.Reservation;
import fr.niji.example.trainresa.domain.entity.reservation.ReservationId;
import fr.niji.example.trainresa.domain.exception.JourneyNotFoundException;
import fr.niji.example.trainresa.domain.exception.NoAvailableSeatsException;
import fr.niji.example.trainresa.domain.repository.JourneyRepository;
import fr.niji.example.trainresa.domain.repository.ReservationRepository;
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

        return new ReserveJourneyResult(reservation);
    }

    public record ReserveJourneyRequest(JourneyId journeyId, String passengerEmail) {
    }

    public record ReserveJourneyResult(Reservation reservation) {
    }
}