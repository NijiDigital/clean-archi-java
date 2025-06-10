package fr.niji.example.trainresa.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import fr.niji.example.trainresa.domain.query.view.JourneyWithAvailability;
import fr.niji.example.trainresa.infrastructure.persistence.entity.JourneyJpaEntity;

@Mapper(componentModel = "spring", uses = JourneyJpaMapper.class)
public abstract class JourneyWithAvailabilityMapper {

    @Autowired
    private JourneyJpaMapper journeyJpaMapper;

    public JourneyWithAvailability toViewModel(JourneyJpaEntity journeyJpaEntity, int activeReservationsCount) {
        var journeyWithAvailability = toViewModel(journeyJpaEntity);
        var journey = journeyJpaMapper.toDomain(journeyJpaEntity);
        return journeyWithAvailability.toBuilder()
                .availableSeats(journey.getAvailableSeats(activeReservationsCount)).build();
    }

    @Mapping(target = "availableSeats", ignore = true) // handled manually
    public abstract JourneyWithAvailability toViewModel(JourneyJpaEntity entity);
}