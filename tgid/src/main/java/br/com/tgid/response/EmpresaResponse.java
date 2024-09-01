package br.com.tgid.response;

public record EmpresaResponse(
        Long id,
        String nome,
        String cnpj,
        String email,
        Double saldo
) {
}
