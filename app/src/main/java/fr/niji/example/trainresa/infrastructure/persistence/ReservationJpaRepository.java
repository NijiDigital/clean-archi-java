package fr.niji.example.trainresa.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import fr.niji.example.trainresa.domain.entity.journey.JourneyId;
import fr.niji.example.trainresa.domain.entity.reservation.Reservation;
import fr.niji.example.trainresa.domain.entity.reservation.ReservationId;
import fr.niji.example.trainresa.domain.entity.reservation.ReservationStatus;
import fr.niji.example.trainresa.domain.repository.ReservationRepository;
import fr.niji.example.trainresa.infrastructure.persistence.entity.ReservationJpaEntity;
import fr.niji.example.trainresa.infrastructure.persistence.mapper.ReservationJpaMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReservationJpaRepository implements ReservationRepository {

    private final SpringDataReservationRepository springDataReservationRepository;
    private final ReservationJpaMapper reservationJpaMapper;

    @Override
    public Reservation save(Reservation reservation) {
        var jpaEntity = reservationJpaMapper.toJpa(reservation);
        var savedEntity = springDataReservationRepository.save(jpaEntity);
        return reservationJpaMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsById(ReservationId reservationId) {
        return springDataReservationRepository.existsById(reservationId.toString());
    }

    @Override
    public int countActiveReservationsByJourneyId(JourneyId journeyId) {
        return springDataReservationRepository.countByJourneyIdAndStatus(
                journeyId.toString(),
                ReservationStatus.CONFIRMED);
    }
}

interface SpringDataReservationRepository extends JpaRepository<ReservationJpaEntity, String> {

    @Query("SELECT COUNT(r) FROM ReservationJpaEntity r WHERE r.journeyId = :journeyId AND r.status = :status")
    int countByJourneyIdAndStatus(@Param("journeyId") String journeyId, @Param("status") ReservationStatus status);
}