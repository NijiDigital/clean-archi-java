package com.example.app.infrastructure.web.mapper;

import com.example.app.domain.query.view.JourneyWithAvailability;
import com.example.app.infrastructure.web.dto.JourneyDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-09T16:29:59+0200",
    comments = "version: 1.6.2, compiler: Eclipse JDT (IDE) 3.42.0.v20250514-1000, environment: Java 21.0.7 (Eclipse Adoptium)"
)
@Component
public class JourneyDTOMapperImpl implements JourneyDTOMapper {

    @Override
    public JourneyDTO toDTO(JourneyWithAvailability source) {
        if ( source == null ) {
            return null;
        }

        JourneyDTO.JourneyDTOBuilder journeyDTO = JourneyDTO.builder();

        journeyDTO.departureStation( mapStationCode( source.getDepartureStationCode() ) );
        journeyDTO.arrivalStation( mapStationCode( source.getArrivalStationCode() ) );
        journeyDTO.arrivalTime( source.getArrivalTime() );
        journeyDTO.availableSeats( source.getAvailableSeats() );
        journeyDTO.departureTime( source.getDepartureTime() );
        journeyDTO.id( source.getId() );
        journeyDTO.trainNumber( source.getTrainNumber() );

        return journeyDTO.build();
    }

    @Override
    public List<JourneyDTO> toDTO(List<JourneyWithAvailability> source) {
        if ( source == null ) {
            return null;
        }

        List<JourneyDTO> list = new ArrayList<JourneyDTO>( source.size() );
        for ( JourneyWithAvailability journeyWithAvailability : source ) {
            list.add( toDTO( journeyWithAvailability ) );
        }

        return list;
    }
}
