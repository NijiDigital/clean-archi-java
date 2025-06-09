package com.example.app.infrastructure.web.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.app.domain.query.view.JourneyWithAvailability;
import com.example.app.infrastructure.web.dto.JourneyDTO;

@Mapper(componentModel = "spring")
public interface JourneyDTOMapper {
        @Mapping(target = "departureStation", source = "departureStationCode")
        @Mapping(target = "arrivalStation", source = "arrivalStationCode")
        JourneyDTO toDTO(JourneyWithAvailability source);

        List<JourneyDTO> toDTO(List<JourneyWithAvailability> source);

        default JourneyDTO.StationDTO mapStationCode(String code) {
                return JourneyDTO.StationDTO.builder()
                                .code(code)
                                .build();
        }
}
