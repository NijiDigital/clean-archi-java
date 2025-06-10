package fr.niji.example.trainresa.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fr.niji.example.trainresa.domain.query.JourneyQuery;
import fr.niji.example.trainresa.domain.repository.JourneyRepository;
import fr.niji.example.trainresa.domain.repository.ReservationRepository;
import fr.niji.example.trainresa.domain.usecase.ListJourneysUseCase;
import fr.niji.example.trainresa.domain.usecase.ReserveJourneyUseCase;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public ListJourneysUseCase listJourneysUseCase(JourneyQuery journeyQuery) {
        return new ListJourneysUseCase(journeyQuery);
    }

    @Bean
    public ReserveJourneyUseCase reserveJourneyUseCase(
            JourneyRepository journeyRepository,
            ReservationRepository reservationRepository) {
        return new ReserveJourneyUseCase(journeyRepository, reservationRepository);
    }
}