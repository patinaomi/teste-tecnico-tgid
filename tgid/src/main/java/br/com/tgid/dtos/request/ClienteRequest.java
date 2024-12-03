package br.com.tgid.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteRequest(
        @NotBlank(message = "O nome do Cliente não pode ser nulo.")
        String nome,

        @NotBlank(message = "O sobrenome do Cliente não pode ser nulo.")
        String sobrenome,

        @NotBlank(message = "O CPF do Cliente não pode ser nulo.")
        @CPF(message = "CPF do cliente inválido")  // para fazer a validação do CPF
        String cpf,

        @Email(message = "Email inválido")
        @NotBlank(message = "O email do Cliente não pode ser nulo.")
        String email,

        Double saldo) {


}
