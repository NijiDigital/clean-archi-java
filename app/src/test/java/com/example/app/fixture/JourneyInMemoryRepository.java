package com.example.app.fixture;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.example.app.domain.entity.journey.Journey;
import com.example.app.domain.entity.journey.JourneyId;
import com.example.app.domain.repository.JourneyRepository;

public class JourneyInMemoryRepository implements JourneyRepository {

    private final Map<JourneyId, Journey> journeys = new HashMap<>();

    @Override
    public Journey save(Journey journey) {
        journeys.put(journey.getId(), journey);
        return journey;
    }

    @Override
    public Optional<Journey> findById(JourneyId id) {
        return Optional.ofNullable(journeys.get(id));
    }

    // Méthodes utilitaires pour les tests
    public void clear() {
        journeys.clear();
    }

    public void addJourney(Journey journey) {
        journeys.put(journey.getId(), journey);
    }

    public boolean contains(JourneyId journeyId) {
        return journeys.containsKey(journeyId);
    }

    public int size() {
        return journeys.size();
    }
}