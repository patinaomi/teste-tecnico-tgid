package br.com.tgid.request;

public record EmpresaRequest(
        String nome,
        String cnpj,
        String email,
        Double saldo
)
{
}
