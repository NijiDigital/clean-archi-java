package fr.niji.example.trainresa.domain.entity.journey;

import java.util.regex.Pattern;

import fr.niji.example.trainresa.domain.exception.InvalidDomainException;

public record TrainNumber(String value) {

    private static final Pattern TRAIN_NUMBER_PATTERN = Pattern.compile("^[A-Z0-9]{3,6}$");

    public TrainNumber {
        validateTrainNumber(value);
    }

    private static void validateTrainNumber(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidDomainException("Train number cannot be null or empty");
        }
        if (!TRAIN_NUMBER_PATTERN.matcher(value).matches()) {
            throw new InvalidDomainException("Train number must be 3-6 alphanumeric characters: " + value);
        }
    }

    public static TrainNumber of(String value) {
        return new TrainNumber(value);
    }
}