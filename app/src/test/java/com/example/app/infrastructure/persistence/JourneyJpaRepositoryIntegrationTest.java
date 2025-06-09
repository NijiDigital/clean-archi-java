package com.example.app.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.example.app.domain.entity.journey.JourneyId;
import com.example.app.domain.entity.reservation.ReservationId;
import com.example.app.domain.entity.reservation.ReservationStatus;
import com.example.app.domain.query.JourneyQuery;
import com.example.app.domain.query.view.JourneyWithAvailability;
import com.example.app.infrastructure.persistence.entity.JourneyJpaEntity;
import com.example.app.infrastructure.persistence.entity.ReservationJpaEntity;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({
                com.example.app.infrastructure.persistence.mapper.CommonJpaMapperImpl.class,
                com.example.app.infrastructure.persistence.mapper.JourneyJpaMapperImpl.class,
                com.example.app.infrastructure.persistence.mapper.JourneyWithAvailabilityMapperImpl.class,
                com.example.app.infrastructure.persistence.query.JourneyJpaQuery.class
})
@ActiveProfiles("docker")
class JourneyJpaRepositoryIntegrationTest {

        @Container
        static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
                        .withDatabaseName("test_db")
                        .withUsername("test")
                        .withPassword("test");

        @DynamicPropertySource
        static void configureProperties(DynamicPropertyRegistry registry) {
                registry.add("spring.datasource.url", postgres::getJdbcUrl);
                registry.add("spring.datasource.username", postgres::getUsername);
                registry.add("spring.datasource.password", postgres::getPassword);
        }

        @Autowired
        private JourneyQuery journeyQuery;

        @Autowired
        private TestEntityManager entityManager;

        @Test
        void should_find_all_journeys_with_availability_calculation() {
                // ARRANGE
                var journey1 = createJourneyJpaEntity("TGV001", "PAR", "LYO", 50);
                var journey2 = createJourneyJpaEntity("TGV002", "LYO", "MAR", 30);

                entityManager.persistAndFlush(journey1);
                entityManager.persistAndFlush(journey2);

                // Créer 3 réservations confirmées sur journey1 (capacité 50)
                var reservation1 = createReservationJpaEntity(journey1.getId(), "john.doe@example.com",
                                ReservationStatus.CONFIRMED);
                var reservation2 = createReservationJpaEntity(journey1.getId(), "jane.smith@example.com",
                                ReservationStatus.CONFIRMED);
                var reservation3 = createReservationJpaEntity(journey1.getId(), "bob.wilson@example.com",
                                ReservationStatus.CONFIRMED);

                // Créer 1 réservation annulée sur journey1 (ne doit pas être comptée)
                var reservationCancelled = createReservationJpaEntity(journey1.getId(),
                                "alice.brown@example.com",
                                ReservationStatus.CANCELLED);

                // Créer 2 réservations confirmées sur journey2 (capacité 30)
                var reservation4 = createReservationJpaEntity(journey2.getId(), "charlie.davis@example.com",
                                ReservationStatus.CONFIRMED);
                var reservation5 = createReservationJpaEntity(journey2.getId(), "eve.johnson@example.com",
                                ReservationStatus.CONFIRMED);

                entityManager.persistAndFlush(reservation1);
                entityManager.persistAndFlush(reservation2);
                entityManager.persistAndFlush(reservation3);
                entityManager.persistAndFlush(reservationCancelled);
                entityManager.persistAndFlush(reservation4);
                entityManager.persistAndFlush(reservation5);

                // ACT
                var journeys = journeyQuery.findAllJourneysWithAvailability();

                // ASSERT
                assertThat(journeys).hasSize(2);

                // Vérifier journey1 : 50 places - 3 réservations confirmées = 47 places
                // disponibles
                var journey1Result = journeys.stream()
                                .filter(j -> j.getTrainNumber().equals("TGV001"))
                                .findFirst()
                                .orElseThrow();

                assertThat(journey1Result.getId()).isEqualTo(journey1.getId());
                assertThat(journey1Result.getCapacity()).isEqualTo(50);
                assertThat(journey1Result.getAvailableSeats()).isEqualTo(47); // 50 - 3 confirmées

                // Vérifier journey2 : 30 places - 2 réservations confirmées = 28 places
                // disponibles
                var journey2Result = journeys.stream()
                                .filter(j -> j.getTrainNumber().equals("TGV002"))
                                .findFirst()
                                .orElseThrow();

                assertThat(journey2Result.getId()).isEqualTo(journey2.getId());
                assertThat(journey2Result.getCapacity()).isEqualTo(30);
                assertThat(journey2Result.getAvailableSeats()).isEqualTo(28); // 30 - 2 confirmées

                // Vérifier que les journeys sont triés par heure de départ
                assertThat(journeys)
                                .extracting(JourneyWithAvailability::getTrainNumber)
                                .containsExactly("TGV001", "TGV002"); // TGV001 part à 10h, TGV002 à 14h
        }

        private JourneyJpaEntity createJourneyJpaEntity(String trainNumber, String depCode,
                        String arrCode, int capacity) {
                return JourneyJpaEntity.builder()
                                .id(JourneyId.generate().toString())
                                .trainNumber(trainNumber)
                                .departureStationCode(depCode)
                                .arrivalStationCode(arrCode)
                                .departureTime(trainNumber.equals("TGV001") ? LocalDateTime.of(2024, 1, 15, 10, 0)
                                                : LocalDateTime.of(2024, 1, 15, 14, 0))
                                .arrivalTime(trainNumber.equals("TGV001") ? LocalDateTime.of(2024, 1, 15, 12, 0)
                                                : LocalDateTime.of(2024, 1, 15, 16, 0))
                                .capacity(capacity)
                                .build();
        }

        private ReservationJpaEntity createReservationJpaEntity(String journeyId, String passengerEmail,
                        ReservationStatus status) {
                return ReservationJpaEntity.builder()
                                .id(ReservationId.generate().toString())
                                .journeyId(journeyId)
                                .passengerEmail(passengerEmail)
                                .createdAt(LocalDateTime.now())
                                .status(status)
                                .build();
        }
}