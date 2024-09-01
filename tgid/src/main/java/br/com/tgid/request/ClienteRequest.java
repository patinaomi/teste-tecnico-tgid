package br.com.tgid.request;

import jakarta.validation.constraints.NotBlank;

public record ClienteRequest(
        @NotBlank String nome,
        @NotBlank String sobrenome,
        @NotBlank String cpf,
        @NotBlank String email,
        @NotBlank Double saldo) {


}
