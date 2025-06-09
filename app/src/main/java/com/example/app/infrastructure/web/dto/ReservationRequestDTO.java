package com.example.app.infrastructure.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ReservationRequestDTO {

    @NotBlank(message = "Journey ID is required")
    String journeyId;

    @NotBlank(message = "Passenger email is required")
    @Email(message = "Invalid email format")
    String passengerEmail;
}