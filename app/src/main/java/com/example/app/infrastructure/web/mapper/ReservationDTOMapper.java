package com.example.app.infrastructure.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.app.domain.entity.reservation.Email;
import com.example.app.domain.entity.reservation.Reservation;
import com.example.app.infrastructure.web.dto.ReservationResponseDTO;

@Mapper(componentModel = "spring", uses = CommonDTOMapper.class)
public interface ReservationDTOMapper {

    @Mapping(target = "passengerEmail", source = "passengerEmail")
    ReservationResponseDTO toDTO(Reservation reservation);

    default String mapEmailToString(Email email) {
        return email.value();
    }
}