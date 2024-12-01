package br.com.tgid.dtos.response;

import br.com.tgid.entity.Transacao;
import br.com.tgid.enums.TipoTransacao;

import java.time.format.DateTimeFormatter;

public record TransacaoResponse(
        Long id,
        Double valor,
        TipoTransacao tipo,
        String data
) {

    // para converter uma entidade Transacao em um TransacaoResponse.
    public static TransacaoResponse fromTransacao(Transacao transacao) {
        // Formata a data para uma string legível
        String dataFormatada = transacao.getData().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

        return new TransacaoResponse(
                transacao.getId(),
                transacao.getValor(),
                transacao.getTipo(),
                dataFormatada
        );
    }
}
