package fr.niji.example.trainresa.infrastructure.web.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import fr.niji.example.trainresa.domain.query.view.JourneyWithAvailability;
import fr.niji.example.trainresa.infrastructure.web.dto.JourneyDTO;

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
