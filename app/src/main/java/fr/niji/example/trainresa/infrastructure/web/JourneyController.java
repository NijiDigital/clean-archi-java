package fr.niji.example.trainresa.infrastructure.web;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.niji.example.trainresa.domain.usecase.ListJourneysUseCase;
import fr.niji.example.trainresa.infrastructure.web.dto.JourneyDTO;
import fr.niji.example.trainresa.infrastructure.web.mapper.JourneyDTOMapper;

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