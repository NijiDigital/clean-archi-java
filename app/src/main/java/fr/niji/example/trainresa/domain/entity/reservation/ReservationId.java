package fr.niji.example.trainresa.domain.entity.reservation;

import java.util.UUID;

import fr.niji.example.trainresa.domain.core.EntityId;

public record ReservationId(UUID value) implements EntityId<UUID> {

    public ReservationId {
        validate(value);
    }

    public static ReservationId generate() {
        return new ReservationId(UUID.randomUUID());
    }

    public static ReservationId of(String value) {
        var uuid = EntityId.parseValue(value, UUID::fromString, "ReservationId");
        return new ReservationId(uuid);
    }

}