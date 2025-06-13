package fr.niji.example.trainresa.infrastructure.web.mapper;

import org.mapstruct.Mapper;

import fr.niji.example.trainresa.domain.core.EntityId;
import fr.niji.example.trainresa.domain.entity.journey.JourneyId;
import fr.niji.example.trainresa.domain.entity.reservation.ReservationId;

@Mapper(componentModel = "spring")
public interface CommonDTOMapper {

    default String mapEntityIdToString(EntityId<?> value) {
        return value.valueString();
    }

    default JourneyId mapStringToJourneyId(String value) {
        return JourneyId.of(value);
    }

    default ReservationId mapStringToReservationId(String value) {
        return ReservationId.of(value);

    }
}