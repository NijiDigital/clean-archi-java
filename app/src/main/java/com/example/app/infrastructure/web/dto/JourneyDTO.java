package com.example.app.infrastructure.web.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

//FIXME: Use record instead of class when vscode java extension is fixed https://github.com/projectlombok/lombok/issues/3883
@Value
@Builder
public class JourneyDTO {
    String id;
    String trainNumber;
    StationDTO departureStation;
    StationDTO arrivalStation;
    LocalDateTime departureTime;
    LocalDateTime arrivalTime;
    int availableSeats;

    @Value
    @Builder
    public static class StationDTO {
        String code;
    }
}