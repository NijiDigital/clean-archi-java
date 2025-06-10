package fr.niji.example.trainresa.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import fr.niji.example.trainresa.domain.entity.journey.Journey;
import fr.niji.example.trainresa.domain.entity.journey.Station;
import fr.niji.example.trainresa.domain.entity.journey.TrainNumber;
import fr.niji.example.trainresa.infrastructure.persistence.entity.JourneyJpaEntity;

@Mapper(componentModel = "spring", uses = CommonJpaMapper.class)
public interface JourneyJpaMapper {
        @Mapping(target = "departureStation", source = "departureStationCode")
        @Mapping(target = "arrivalStation", source = "arrivalStationCode")
        Journey toDomain(JourneyJpaEntity source);

        @Mapping(target = "departureStationCode", source = "departureStation")
        @Mapping(target = "arrivalStationCode", source = "arrivalStation")
        JourneyJpaEntity toJpa(Journey source);

        default TrainNumber mapStringToTrainNumber(String value) {
                return TrainNumber.of(value);
        }

        default String mapTrainNumberToString(TrainNumber value) {
                return value.value();
        }

        default Station mapStation(String code) {
                return Station.of(code);
        }

        default String mapStationToString(Station station) {
                return station.code();
        }

}