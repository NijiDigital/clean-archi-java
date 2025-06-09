package com.example.app.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.app.domain.query.JourneyQuery;
import com.example.app.domain.usecase.ListJourneysUseCase;
import com.example.app.fixture.JourneyWithAvailabilityFixture;

@ExtendWith(MockitoExtension.class)
class ListJourneysUseCaseTest {

    @Mock
    private JourneyQuery journeyQuery; // Mock pour les queries (lecture)

    private ListJourneysUseCase useCase;

    @BeforeEach
    void setUp() {
        useCase = new ListJourneysUseCase(journeyQuery);
    }

    @Test
    void should_return_all_journeys_with_availability_when_journeys_exist() {
        // Arrange
        var journey1 = JourneyWithAvailabilityFixture.journeyWithAvailability(
                "1", "TGV001", "PAR", "LYO", 50, 40);
        var journey2 = JourneyWithAvailabilityFixture.journeyWithAvailability(
                "2", "TGV002", "LYO", "MAR", 30, 25);

        var expectedJourneys = List.of(journey1, journey2);

        // Mock de la query (lecture de données complexes)
        when(journeyQuery.findAllJourneysWithAvailability()).thenReturn(expectedJourneys);

        var request = new ListJourneysUseCase.ListJourneysRequest();

        // Act
        var result = useCase.execute(request);

        // Assert
        assertThat(result.journeys()).hasSize(2);
        assertThat(result.journeys()).containsExactly(journey1, journey2);
    }

}