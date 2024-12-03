package br.com.tgid.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.br.CNPJ;

public record EmpresaRequest(

        @NotBlank(message = "O nome da Empresa não pode ser nulo.")
        String nome,

        @CNPJ(message = "CNPJ inválido")
        @NotBlank(message = "O CNPJ da Empresa não pode ser nulo.")
        String cnpj,

        @Email(message = "Email inválido")
        @NotBlank(message = "O email da Empresa não pode ser nulo.")
        String email,

        @NotNull(message = "O saldo da Empresa é obrigatório.")
        @Positive(message = "O saldo da Empresa deve ser positivo.")
        Double saldo
)
{
}
