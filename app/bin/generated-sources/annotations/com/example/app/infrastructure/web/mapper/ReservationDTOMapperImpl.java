package com.example.app.infrastructure.web.mapper;

import com.example.app.domain.entity.reservation.Reservation;
import com.example.app.infrastructure.web.dto.ReservationResponseDTO;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-09T16:29:59+0200",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class ReservationDTOMapperImpl implements ReservationDTOMapper {

    @Autowired
    private CommonDTOMapper commonDTOMapper;

    @Override
    public ReservationResponseDTO toDTO(Reservation reservation) {
        if ( reservation == null ) {
            return null;
        }

        ReservationResponseDTO.ReservationResponseDTOBuilder reservationResponseDTO = ReservationResponseDTO.builder();

        reservationResponseDTO.passengerEmail( mapEmailToString( reservation.getPassengerEmail() ) );
        reservationResponseDTO.createdAt( reservation.getCreatedAt() );
        reservationResponseDTO.id( commonDTOMapper.mapReservationIdToString( reservation.getId() ) );
        reservationResponseDTO.journeyId( commonDTOMapper.mapJourneyIdToString( reservation.getJourneyId() ) );

        return reservationResponseDTO.build();
    }
}
