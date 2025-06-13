package fr.niji.example.trainresa.domain.core;

import java.util.function.Function;

import fr.niji.example.trainresa.domain.exception.InvalidDomainException;

public interface EntityId<T> {

    T value();

    default void validate(T value) {
        if (value == null) {
            throw new InvalidDomainException(this.getClass().getSimpleName() + " cannot be null");
        }
    }

    static <T> T parseValue(String value, Function<String, T> parser, String typeName) {
        if (value == null || value.isBlank()) {
            throw new InvalidDomainException(typeName + " string cannot be null or blank");
        }
        try {
            return parser.apply(value);
        } catch (Exception e) {
            throw new InvalidDomainException("Invalid " + typeName + " format: " + value);
        }
    }

    default String valueString() {
        return value().toString();
    }

}