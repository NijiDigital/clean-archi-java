package com.example.app.infrastructure.web.mapper;

import org.mapstruct.Mapper;

import com.example.app.domain.entity.journey.JourneyId;
import com.example.app.domain.entity.reservation.ReservationId;

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