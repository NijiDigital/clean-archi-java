package com.example.app.domain.repository;

import java.util.Optional;

import com.example.app.domain.entity.journey.Journey;
import com.example.app.domain.entity.journey.JourneyId;

public interface JourneyRepository {

    Optional<Journey> findById(JourneyId journeyId);

    Journey save(Journey journey);
}