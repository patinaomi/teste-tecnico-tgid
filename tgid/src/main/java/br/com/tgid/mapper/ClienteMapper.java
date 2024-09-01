package br.com.tgid.mapper;

import br.com.tgid.entity.Cliente;
import br.com.tgid.request.ClienteRequest;
import br.com.tgid.response.ClienteResponse;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteRequest request) {
        return new Cliente(
                null,
                request.nome(),
                request.sobrenome(),
                request.cpf(),
                request.email(),
                request.saldo()
        );
    }

    public ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getSobrenome(),
                cliente.getCpf(),
                cliente.getEmail(),
                cliente.getSaldo()
        );
    }
}
