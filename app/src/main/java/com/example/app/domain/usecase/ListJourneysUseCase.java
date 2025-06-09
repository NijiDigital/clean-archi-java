package com.example.app.domain.usecase;

import java.util.List;

import com.example.app.domain.query.JourneyQuery;
import com.example.app.domain.query.view.JourneyWithAvailability;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ListJourneysUseCase {

    private final JourneyQuery journeyQuery;

    @Transactional
    public ListJourneysResult execute(ListJourneysRequest request) {
        // Utilisation de la requête optimisée avec JPQL
        var journeysWithAvailability = journeyQuery.findAllJourneysWithAvailability();

        return new ListJourneysResult(journeysWithAvailability);
    }

    public record ListJourneysRequest() {
        // Pas de paramètres pour le moment, mais record préparé pour d'éventuels
        // filtres
    }

    public record ListJourneysResult(List<JourneyWithAvailability> journeys) {
    }
}