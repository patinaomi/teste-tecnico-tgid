package br.com.tgid.response;

public record ClienteResponse(
        Long id,
        String nome,
        String sobrenome,
        String cpf,
        String email,
        Double saldo) {
}
