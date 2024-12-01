package br.com.tgid.dtos.request;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CNPJ;

public record EmpresaRequest(
        String nome,

        @CNPJ
        String cnpj,

        @Email
        String email,
        Double saldo
)
{
}
