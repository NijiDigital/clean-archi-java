package com.example.app.infrastructure.persistence.mapper;

import com.example.app.domain.query.view.JourneyWithAvailability;
import com.example.app.infrastructure.persistence.entity.JourneyJpaEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-09T16:29:59+0200",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class JourneyWithAvailabilityMapperImpl extends JourneyWithAvailabilityMapper {

    @Override
    public JourneyWithAvailability toViewModel(JourneyJpaEntity entity) {
        if ( entity == null ) {
            return null;
        }

        JourneyWithAvailability.JourneyWithAvailabilityBuilder journeyWithAvailability = JourneyWithAvailability.builder();

        journeyWithAvailability.arrivalStationCode( entity.getArrivalStationCode() );
        journeyWithAvailability.arrivalTime( entity.getArrivalTime() );
        if ( entity.getCapacity() != null ) {
            journeyWithAvailability.capacity( entity.getCapacity() );
        }
        journeyWithAvailability.departureStationCode( entity.getDepartureStationCode() );
        journeyWithAvailability.departureTime( entity.getDepartureTime() );
        journeyWithAvailability.id( entity.getId() );
        journeyWithAvailability.trainNumber( entity.getTrainNumber() );

        return journeyWithAvailability.build();
    }
}
