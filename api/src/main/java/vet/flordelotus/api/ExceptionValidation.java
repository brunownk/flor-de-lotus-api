package vet.flordelotus.api;

public class ExceptionValidation extends RuntimeException {
    public ExceptionValidation(String mensagem) {
        super(mensagem);
    }
}
