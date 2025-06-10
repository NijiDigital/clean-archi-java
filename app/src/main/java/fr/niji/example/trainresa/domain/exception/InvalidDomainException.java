package fr.niji.example.trainresa.domain.exception;

public class InvalidDomainException extends IllegalArgumentException {

    public InvalidDomainException(String message) {
        super(message);
    }

    public InvalidDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}