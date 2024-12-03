package br.com.tgid.dtos.request;

import br.com.tgid.enums.TipoTaxa;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SaqueRequest(

        @NotNull(message = "O id da empresa não pode ser nulo")
        Long empresaId,

        @NotNull(message = "O valor do saque não pode ser nulo")
        @Positive(message = "O valor do saque deve ser maior que zero")
        Double valor,

        @NotBlank(message = "O tipo da taxa é obrigatório.")
        TipoTaxa tipoTaxa

        ) {
}
