package com.example.app.infrastructure.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.domain.usecase.ListJourneysUseCase;
import com.example.app.infrastructure.web.dto.JourneyDTO;
import com.example.app.infrastructure.web.mapper.JourneyDTOMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/journeys")
@RequiredArgsConstructor
public class JourneyController {

        private final ListJourneysUseCase listJourneysUseCase;
        private final JourneyDTOMapper journeyMapper;

        @GetMapping
        public ResponseEntity<List<JourneyDTO>> getAllJourneys() {
                ListJourneysUseCase.ListJourneysRequest request = new ListJourneysUseCase.ListJourneysRequest();
                ListJourneysUseCase.ListJourneysResult result = listJourneysUseCase.execute(request);

                // Mapping du View Model vers DTO pour maintenir la structure d'API
                var journeyDTOs = journeyMapper.toDTO(result.journeys());

                return ResponseEntity.ok(journeyDTOs);
        }
}