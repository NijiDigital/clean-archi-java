package com.example.app.infrastructure.persistence.mapper;

import com.example.app.domain.entity.journey.Journey;
import com.example.app.infrastructure.persistence.entity.JourneyJpaEntity;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-09T16:29:59+0200",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class JourneyJpaMapperImpl implements JourneyJpaMapper {

    @Autowired
    private CommonJpaMapper commonJpaMapper;

    @Override
    public Journey toDomain(JourneyJpaEntity source) {
        if ( source == null ) {
            return null;
        }

        Journey.JourneyBuilder journey = Journey.builder();

        journey.departureStation( mapStation( source.getDepartureStationCode() ) );
        journey.arrivalStation( mapStation( source.getArrivalStationCode() ) );
        journey.arrivalTime( source.getArrivalTime() );
        if ( source.getCapacity() != null ) {
            journey.capacity( source.getCapacity() );
        }
        journey.departureTime( source.getDepartureTime() );
        journey.id( commonJpaMapper.mapStringToJourneyId( source.getId() ) );
        journey.trainNumber( mapStringToTrainNumber( source.getTrainNumber() ) );

        return journey.build();
    }

    @Override
    public JourneyJpaEntity toJpa(Journey source) {
        if ( source == null ) {
            return null;
        }

        JourneyJpaEntity.JourneyJpaEntityBuilder journeyJpaEntity = JourneyJpaEntity.builder();

        journeyJpaEntity.departureStationCode( mapStationToString( source.getDepartureStation() ) );
        journeyJpaEntity.arrivalStationCode( mapStationToString( source.getArrivalStation() ) );
        journeyJpaEntity.arrivalTime( source.getArrivalTime() );
        journeyJpaEntity.capacity( source.getCapacity() );
        journeyJpaEntity.departureTime( source.getDepartureTime() );
        journeyJpaEntity.id( commonJpaMapper.mapJourneyIdToString( source.getId() ) );
        journeyJpaEntity.trainNumber( mapTrainNumberToString( source.getTrainNumber() ) );

        return journeyJpaEntity.build();
    }
}
