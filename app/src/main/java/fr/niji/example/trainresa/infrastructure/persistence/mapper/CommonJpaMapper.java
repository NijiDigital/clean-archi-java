package fr.niji.example.trainresa.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import fr.niji.example.trainresa.domain.core.EntityId;
import fr.niji.example.trainresa.domain.entity.journey.JourneyId;

@Mapper(componentModel = "spring")
public interface CommonJpaMapper {

        default JourneyId mapStringToJourneyId(String value) {
                return JourneyId.of(value);
        }

        default String mapEntityIdToString(EntityId<?> value) {
                return value.valueString();
        }

}