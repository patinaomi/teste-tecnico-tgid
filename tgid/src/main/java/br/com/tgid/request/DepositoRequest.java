package br.com.tgid.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DepositoRequest(
        @NotNull(message = "O ID do cliente é obrigatório.")
        Long clienteId,

        @NotNull(message = "O ID da empresa é obrigatório.")
        Long empresaId,

        @NotNull(message = "O valor do depósito é obrigatório.")
        @Positive(message = "O valor do depósito deve ser positivo.")
        Double valor  // Se aplicável
) {
}
