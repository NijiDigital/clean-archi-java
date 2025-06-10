package fr.niji.example.trainresa.infrastructure.web.mapper;

import org.mapstruct.Mapper;

import fr.niji.example.trainresa.domain.entity.journey.JourneyId;
import fr.niji.example.trainresa.domain.entity.reservation.ReservationId;

@Mapper(componentModel = "spring")
public interface CommonDTOMapper {

    default JourneyId mapStringToJourneyId(String value) {
        return JourneyId.of(value);
    }

    default String mapJourneyIdToString(JourneyId value) {
        return value.toString();
    }

    default ReservationId mapStringToReservationId(String value) {
        return ReservationId.of(value);

    }

    default String mapReservationIdToString(ReservationId value) {
        return value.toString();

    }
}