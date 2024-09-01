package br.com.tgid.service.impl;

import br.com.tgid.repository.ClienteRepository;
import br.com.tgid.service.ClienteService;
import br.com.tgid.entity.Cliente;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private ClienteRepository clienteRepository;

    @Override
    public Cliente criar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}
