package br.com.tgid.dtos.response;

public record EmpresaResponse(
        Long id,
        String nome,
        String cnpj,
        String email,
        Double saldo
) {
}
