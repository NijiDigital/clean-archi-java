package fr.niji.example.trainresa.domain.entity.journey;

import java.util.UUID;

import fr.niji.example.trainresa.domain.core.EntityId;

public record JourneyId(UUID value) implements EntityId<UUID> {

    public JourneyId {
        validate(value);
    }

    public static JourneyId generate() {
        return new JourneyId(UUID.randomUUID());
    }

    public static JourneyId of(String value) {
        var uuid = EntityId.parseValue(value, UUID::fromString, "JourneyId");
        return new JourneyId(uuid);
    }
}