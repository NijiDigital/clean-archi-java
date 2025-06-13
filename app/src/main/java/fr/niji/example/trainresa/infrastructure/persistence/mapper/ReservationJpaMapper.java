package fr.niji.example.trainresa.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import fr.niji.example.trainresa.domain.entity.reservation.Email;
import fr.niji.example.trainresa.domain.entity.reservation.Reservation;
import fr.niji.example.trainresa.domain.entity.reservation.ReservationId;
import fr.niji.example.trainresa.infrastructure.persistence.entity.ReservationJpaEntity;

@Mapper(componentModel = "spring", uses = CommonJpaMapper.class)
public interface ReservationJpaMapper {
    @Mapping(target = "passengerEmail", source = "passengerEmail")
    Reservation toDomain(ReservationJpaEntity source);

    @Mapping(target = "passengerEmail", source = "passengerEmail")
    ReservationJpaEntity toJpa(Reservation source);

    default ReservationId mapStringToReservationId(String value) {
        return ReservationId.of(value);

    }

    default Email mapStringToEmail(String value) {
        return Email.of(value);
    }

    default String mapEmailToString(Email value) {
        return value.value();
    }

}