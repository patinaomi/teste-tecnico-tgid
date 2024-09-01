package br.com.tgid.request;

import br.com.tgid.enums.TipoTransacao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransacaoRequest(
        @NotNull(message = "O tipo de transação é obrigatório.")
        TipoTransacao tipoTransacao,

        @NotNull(message = "O valor da transação é obrigatório.")
        @Positive(message = "O valor da transação deve ser positivo.")
        Double valor,

        @NotNull(message = "O ID do cliente é obrigatório.")
        Long clienteId,

        @NotNull(message = "O ID da empresa é obrigatório.")
        Long empresaId
) {
}
