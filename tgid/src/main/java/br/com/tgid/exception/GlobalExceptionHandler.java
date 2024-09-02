package br.com.tgid.exception;

import br.com.tgid.service.impl.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final EmailServiceImpl emailService;

    @Value("${spring.mail.username}")
    private String remetenteEmail;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex, WebRequest request) {
        return new ResponseEntity<>("Erro interno no servidor. Tente novamente.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(TransacaoException.class)
    public ResponseEntity<String> handleTransacaoException(TransacaoException ex) {
        // Envia um e-mail apenas para erros de transação
        emailService.enviarEmail(remetenteEmail, "Erro na Transação",
                "Ocorreu um erro durante uma transação: " + ex.getMessage());

        return new ResponseEntity<>("Ocorreu um erro ao processar sua transação. Por favor, tente novamente mais tarde.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
