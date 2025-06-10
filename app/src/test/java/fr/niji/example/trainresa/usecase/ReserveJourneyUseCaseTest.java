package fr.niji.example.trainresa.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.niji.example.trainresa.domain.entity.journey.JourneyId;
import fr.niji.example.trainresa.domain.exception.JourneyNotFoundException;
import fr.niji.example.trainresa.domain.exception.NoAvailableSeatsException;
import fr.niji.example.trainresa.domain.usecase.ReserveJourneyUseCase;
import fr.niji.example.trainresa.fixture.JourneyFixture;
import fr.niji.example.trainresa.fixture.JourneyInMemoryRepository;
import fr.niji.example.trainresa.fixture.ReservationFixture;
import fr.niji.example.trainresa.fixture.ReservationInMemoryRepository;

class ReserveJourneyUseCaseTest {

        private JourneyInMemoryRepository journeyRepository;
        private ReservationInMemoryRepository reservationRepository;
        private ReserveJourneyUseCase useCase;

        @BeforeEach
        void setUp() {
                journeyRepository = new JourneyInMemoryRepository();
                reservationRepository = new ReservationInMemoryRepository();
                useCase = new ReserveJourneyUseCase(journeyRepository, reservationRepository);
        }

        @Test
        void should_create_reservation_when_journey_exists_and_has_available_seats() {
                // Arrange
                // given a journey and a passenger
                var journeyId = JourneyId.generate();
                var passengerEmail = "john.doe@example.com";
                var journey = JourneyFixture.journeyWithCapacity(journeyId, 10);

                journeyRepository.addJourney(journey);

                // Ajouter 5 réservations existantes
                for (int i = 0; i < 5; i++) {
                        var existingReservation = ReservationFixture
                                        .confirmedReservation(journeyId, "passenger" + i + "@example.com");
                        reservationRepository.addReservation(existingReservation);
                }

                // when the passenger reserves the journey
                var request = new ReserveJourneyUseCase.ReserveJourneyRequest(
                                journeyId,
                                passengerEmail);

                var result = useCase.execute(request);

                // Assert
                assertThat(result.reservation()).isNotNull();
                assertThat(result.reservation().getJourneyId()).isEqualTo(journeyId);
                assertThat(result.reservation().getPassengerEmail().value()).isEqualTo(passengerEmail);
                assertThat(result.reservation().getCreatedAt()).isNotNull();

                // Vérifier que la réservation a été sauvegardée
                assertThat(reservationRepository.size()).isEqualTo(6); // 5 + 1 nouvelle
                assertThat(reservationRepository.countActiveReservationsByJourneyId(journeyId)).isEqualTo(6);
        }

        @Test
        void should_throw_journey_not_found_exception_when_journey_does_not_exist() {
                // Arrange
                var journeyId = JourneyId.generate();
                var passengerEmail = "john.doe@example.com";

                var request = new ReserveJourneyUseCase.ReserveJourneyRequest(
                                journeyId,
                                passengerEmail);

                // Act & Assert
                assertThatThrownBy(() -> useCase.execute(request))
                                .isInstanceOf(JourneyNotFoundException.class)
                                .hasMessageContaining(journeyId.toString());

                assertThat(reservationRepository.size()).isEqualTo(0);
        }

        @Test
        void should_throw_no_available_seats_exception_when_journey_has_no_seats() {
                // Arrange
                var journeyId = JourneyId.generate();
                var passengerEmail = "john.doe@example.com";
                var journey = JourneyFixture.journeyWithCapacity(journeyId, 10);

                journeyRepository.addJourney(journey);

                // Ajouter 10 réservations pour atteindre la capacité
                for (int i = 0; i < 10; i++) {
                        var existingReservation = ReservationFixture
                                        .confirmedReservation(journeyId, "passenger" + i + "@example.com");
                        reservationRepository.addReservation(existingReservation);
                }

                var request = new ReserveJourneyUseCase.ReserveJourneyRequest(
                                journeyId,
                                passengerEmail);

                // Act & Assert
                assertThatThrownBy(() -> useCase.execute(request))
                                .isInstanceOf(NoAvailableSeatsException.class)
                                .hasMessageContaining(journeyId.toString());

                assertThat(reservationRepository.size()).isEqualTo(10); // Pas de nouvelle réservation
        }
}