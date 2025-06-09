package com.example.app.infrastructure.persistence.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "journeys")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JourneyJpaEntity {

    @Id
    private String id;

    @Column(name = "train_number", nullable = false, length = 6)
    private String trainNumber;

    @Column(name = "departure_station_code", nullable = false, length = 3)
    private String departureStationCode;

    @Column(name = "arrival_station_code", nullable = false, length = 3)
    private String arrivalStationCode;

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;
}