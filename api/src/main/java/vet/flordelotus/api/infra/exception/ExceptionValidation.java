package vet.flordelotus.api.infra.exception;

public class ExceptionValidation extends RuntimeException {
    public ExceptionValidation(String message) {
        super(message);
    }
}
