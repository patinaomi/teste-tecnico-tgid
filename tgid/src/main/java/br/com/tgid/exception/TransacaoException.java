package br.com.tgid.exception;

public class TransacaoException extends RuntimeException {
    public TransacaoException(String message) {
        super(message);
    }
}
