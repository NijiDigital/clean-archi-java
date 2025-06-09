package com.example.app.infrastructure.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.domain.entity.journey.JourneyId;
import com.example.app.domain.usecase.ReserveJourneyUseCase;
import com.example.app.infrastructure.web.dto.ReservationRequestDTO;
import com.example.app.infrastructure.web.dto.ReservationResponseDTO;
import com.example.app.infrastructure.web.mapper.ReservationDTOMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReserveJourneyUseCase reserveJourneyUseCase;
    private final ReservationDTOMapper reservationMapper;

    @PostMapping
    public ResponseEntity<ReservationResponseDTO> createReservation(
            @Valid @RequestBody ReservationRequestDTO request) {

        ReserveJourneyUseCase.ReserveJourneyRequest useCaseRequest = new ReserveJourneyUseCase.ReserveJourneyRequest(
                JourneyId.of(request.getJourneyId()),
                request.getPassengerEmail());

        ReserveJourneyUseCase.ReserveJourneyResult result = reserveJourneyUseCase.execute(useCaseRequest);

        var response = reservationMapper.toDTO(result.reservation());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}