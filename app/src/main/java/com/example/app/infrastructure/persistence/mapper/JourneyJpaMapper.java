package com.example.app.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.app.domain.entity.journey.Journey;
import com.example.app.domain.entity.journey.Station;
import com.example.app.domain.entity.journey.TrainNumber;
import com.example.app.infrastructure.persistence.entity.JourneyJpaEntity;

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