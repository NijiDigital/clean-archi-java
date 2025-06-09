package com.example.app.infrastructure.web.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReservationResponseDTO {
    String id;
    String journeyId;
    String passengerEmail;
    LocalDateTime createdAt;
}