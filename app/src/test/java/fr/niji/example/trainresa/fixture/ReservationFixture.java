package fr.niji.example.trainresa.fixture;

import java.time.LocalDateTime;

import fr.niji.example.trainresa.domain.entity.journey.JourneyId;
import fr.niji.example.trainresa.domain.entity.reservation.Email;
import fr.niji.example.trainresa.domain.entity.reservation.Reservation;
import fr.niji.example.trainresa.domain.entity.reservation.ReservationId;
import fr.niji.example.trainresa.domain.entity.reservation.ReservationStatus;

public class ReservationFixture {

    public static Reservation.ReservationBuilder defaultBuilder() {
        return Reservation.builder()
                .id(ReservationId.generate())
                .journeyId(JourneyId.generate())
                .passengerEmail(Email.of("passenger@example.com"))
                .createdAt(LocalDateTime.now())
                .status(ReservationStatus.CONFIRMED);
    }

    public static Reservation defaultReservation() {
        return defaultBuilder().build();
    }

    public static Reservation reservationForJourney(JourneyId journeyId) {
        return defaultBuilder()
                .journeyId(journeyId)
                .build();
    }

    public static Reservation reservationWithEmail(String email) {
        return defaultBuilder()
                .passengerEmail(Email.of(email))
                .build();
    }

    public static Reservation reservationWithStatus(ReservationStatus status) {
        return defaultBuilder()
                .status(status)
                .build();
    }

    public static Reservation reservationForJourneyWithEmail(JourneyId journeyId, String email) {
        return defaultBuilder()
                .journeyId(journeyId)
                .passengerEmail(Email.of(email))
                .build();
    }

    public static Reservation confirmedReservation(JourneyId journeyId, String email) {
        return defaultBuilder()
                .journeyId(journeyId)
                .passengerEmail(Email.of(email))
                .status(ReservationStatus.CONFIRMED)
                .build();
    }

    public static Reservation cancelledReservation(JourneyId journeyId, String email) {
        return defaultBuilder()
                .journeyId(journeyId)
                .passengerEmail(Email.of(email))
                .status(ReservationStatus.CANCELLED)
                .build();
    }
}