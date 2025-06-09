package com.example.app.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.app.domain.query.JourneyQuery;
import com.example.app.domain.repository.JourneyRepository;
import com.example.app.domain.repository.ReservationRepository;
import com.example.app.domain.usecase.ListJourneysUseCase;
import com.example.app.domain.usecase.ReserveJourneyUseCase;

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