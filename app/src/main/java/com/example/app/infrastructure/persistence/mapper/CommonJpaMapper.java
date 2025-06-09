package com.example.app.infrastructure.persistence.mapper;

import org.mapstruct.Mapper;

import com.example.app.domain.entity.journey.JourneyId;

@Mapper(componentModel = "spring")
public interface CommonJpaMapper {

        default JourneyId mapStringToJourneyId(String value) {
                return JourneyId.of(value);
        }

        default String mapJourneyIdToString(JourneyId value) {
                return value.toString();
        }

}