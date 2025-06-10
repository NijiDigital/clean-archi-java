package fr.niji.example.trainresa.infrastructure.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.niji.example.trainresa.domain.entity.journey.Journey;
import fr.niji.example.trainresa.domain.entity.journey.JourneyId;
import fr.niji.example.trainresa.domain.repository.JourneyRepository;
import fr.niji.example.trainresa.infrastructure.persistence.entity.JourneyJpaEntity;
import fr.niji.example.trainresa.infrastructure.persistence.mapper.JourneyJpaMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JourneyJpaRepository implements JourneyRepository {

    private final SpringDataJourneyRepository springDataJourneyRepository;
    private final JourneyJpaMapper journeyJpaMapper;

    @Override
    public Optional<Journey> findById(JourneyId journeyId) {
        return springDataJourneyRepository.findById(journeyId.toString())
                .map(journeyJpaMapper::toDomain);
    }

    @Override
    public Journey save(Journey journey) {
        var jpaEntity = journeyJpaMapper.toJpa(journey);
        var savedEntity = springDataJourneyRepository.save(jpaEntity);
        return journeyJpaMapper.toDomain(savedEntity);
    }
}

interface SpringDataJourneyRepository extends JpaRepository<JourneyJpaEntity, String> {
}