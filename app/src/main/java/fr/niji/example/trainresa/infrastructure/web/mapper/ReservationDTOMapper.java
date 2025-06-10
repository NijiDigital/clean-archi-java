package fr.niji.example.trainresa.infrastructure.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import fr.niji.example.trainresa.domain.entity.reservation.Email;
import fr.niji.example.trainresa.domain.entity.reservation.Reservation;
import fr.niji.example.trainresa.infrastructure.web.dto.ReservationResponseDTO;

@Mapper(componentModel = "spring", uses = CommonDTOMapper.class)
public interface ReservationDTOMapper {

    @Mapping(target = "passengerEmail", source = "passengerEmail")
    ReservationResponseDTO toDTO(Reservation reservation);

    default String mapEmailToString(Email email) {
        return email.value();
    }
}