package com.example.app.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.app.domain.entity.reservation.Email;
import com.example.app.domain.entity.reservation.Reservation;
import com.example.app.domain.entity.reservation.ReservationId;
import com.example.app.infrastructure.persistence.entity.ReservationJpaEntity;

@Mapper(componentModel = "spring", uses = CommonJpaMapper.class)
public interface ReservationJpaMapper {
    @Mapping(target = "passengerEmail", source = "passengerEmail")
    Reservation toDomain(ReservationJpaEntity source);

    @Mapping(target = "passengerEmail", source = "passengerEmail")
    ReservationJpaEntity toJpa(Reservation source);

    default ReservationId mapStringToReservationId(String value) {
        return ReservationId.of(value);

    }

    default String mapReservationIdToString(ReservationId value) {
        return value.toString();

    }

    default Email mapStringToEmail(String value) {
        return Email.of(value);
    }

    default String mapEmailToString(Email value) {
        return value.value();
    }

}