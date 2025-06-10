package fr.niji.example.trainresa.domain.repository;

import java.util.Optional;

import fr.niji.example.trainresa.domain.entity.journey.Journey;
import fr.niji.example.trainresa.domain.entity.journey.JourneyId;

public interface JourneyRepository {

    Optional<Journey> findById(JourneyId journeyId);

    Journey save(Journey journey);
}