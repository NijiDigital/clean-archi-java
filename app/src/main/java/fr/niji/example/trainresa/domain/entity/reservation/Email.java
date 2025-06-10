package fr.niji.example.trainresa.domain.entity.reservation;

import java.util.regex.Pattern;

import fr.niji.example.trainresa.domain.exception.InvalidDomainException;

public record Email(String value) {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE);

    public Email {
        validateEmail(value);
    }

    private static void validateEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new InvalidDomainException("Email cannot be null or empty");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new InvalidDomainException("Invalid email format");
        }
    }

    public static Email of(String value) {
        return new Email(value);
    }
}