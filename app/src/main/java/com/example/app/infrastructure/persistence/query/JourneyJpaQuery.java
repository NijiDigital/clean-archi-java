package com.example.app.infrastructure.persistence.query;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.app.domain.entity.reservation.ReservationStatus;
import com.example.app.domain.query.JourneyQuery;
import com.example.app.domain.query.view.JourneyWithAvailability;
import com.example.app.infrastructure.persistence.entity.JourneyJpaEntity;
import com.example.app.infrastructure.persistence.mapper.JourneyWithAvailabilityMapper;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JourneyJpaQuery implements JourneyQuery {

    @PersistenceContext
    private final EntityManager entityManager;

    private final JourneyWithAvailabilityMapper journeyWithAvailabilityMapper;

    @Override
    public List<JourneyWithAvailability> findAllJourneysWithAvailability() {
        // Requête optimisée avec LEFT JOIN et COUNT pour éviter N+1
        var jpql = """
                SELECT j, COALESCE(COUNT(r), 0) as reservationCount
                FROM JourneyJpaEntity j
                LEFT JOIN ReservationJpaEntity r ON r.journeyId = j.id AND r.status = :status
                GROUP BY j.id, j.trainNumber, j.departureStationCode,
                         j.arrivalStationCode, j.departureTime, j.arrivalTime, j.capacity
                ORDER BY j.departureTime
                """;

        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
        query.setParameter("status", ReservationStatus.CONFIRMED);

        List<Object[]> results = query.getResultList();

        return results.stream()
                .map(this::toJourneyWithAvailability)
                .toList();
    }

    private JourneyWithAvailability toJourneyWithAvailability(Object[] result) {
        var journeyJpaEntity = (JourneyJpaEntity) result[0];
        var reservationCount = (Long) result[1];

        var activeReservations = reservationCount.intValue();

        return journeyWithAvailabilityMapper.toViewModel(journeyJpaEntity, activeReservations);
    }
}