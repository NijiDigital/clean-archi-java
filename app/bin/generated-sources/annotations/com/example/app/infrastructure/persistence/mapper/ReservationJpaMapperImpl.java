package com.example.app.infrastructure.persistence.mapper;

import com.example.app.domain.entity.reservation.Reservation;
import com.example.app.infrastructure.persistence.entity.ReservationJpaEntity;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-09T16:29:59+0200",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class ReservationJpaMapperImpl implements ReservationJpaMapper {

    @Autowired
    private CommonJpaMapper commonJpaMapper;

    @Override
    public Reservation toDomain(ReservationJpaEntity source) {
        if ( source == null ) {
            return null;
        }

        Reservation.ReservationBuilder reservation = Reservation.builder();

        reservation.passengerEmail( mapStringToEmail( source.getPassengerEmail() ) );
        reservation.createdAt( source.getCreatedAt() );
        reservation.id( mapStringToReservationId( source.getId() ) );
        reservation.journeyId( commonJpaMapper.mapStringToJourneyId( source.getJourneyId() ) );
        reservation.status( source.getStatus() );

        return reservation.build();
    }

    @Override
    public ReservationJpaEntity toJpa(Reservation source) {
        if ( source == null ) {
            return null;
        }

        ReservationJpaEntity.ReservationJpaEntityBuilder reservationJpaEntity = ReservationJpaEntity.builder();

        reservationJpaEntity.passengerEmail( mapEmailToString( source.getPassengerEmail() ) );
        reservationJpaEntity.createdAt( source.getCreatedAt() );
        reservationJpaEntity.id( mapReservationIdToString( source.getId() ) );
        reservationJpaEntity.journeyId( commonJpaMapper.mapJourneyIdToString( source.getJourneyId() ) );
        reservationJpaEntity.status( source.getStatus() );

        return reservationJpaEntity.build();
    }
}
