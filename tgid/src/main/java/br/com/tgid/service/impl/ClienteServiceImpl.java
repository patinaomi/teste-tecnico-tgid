package br.com.tgid.service.impl;

import br.com.tgid.repository.ClienteRepository;
import br.com.tgid.service.ClienteService;
import br.com.tgid.entity.Cliente;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private ClienteRepository clienteRepository;

    @Override
    public Cliente criar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Cliente realizarDeposito(Long clienteId, Double valor) {
        return null;
    }

    @Override
    public Cliente realizarSaque(Long clienteId, Double valor) {
        return null;
    }

}
