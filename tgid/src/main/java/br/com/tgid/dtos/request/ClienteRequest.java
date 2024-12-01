package br.com.tgid.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteRequest(
        @NotBlank
        String nome,

        @NotBlank
        String sobrenome,

        @NotBlank
        @CPF  // para fazer a validação do CPF
        String cpf,

        @Email
        @NotBlank
        String email,

        @NotBlank
        Double saldo) {


}
