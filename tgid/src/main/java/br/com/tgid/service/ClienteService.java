package br.com.tgid.service;


import br.com.tgid.entity.Cliente;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClienteService {

    Cliente criar(Cliente cliente);
    Cliente realizarDeposito(Long clienteId, Double valor);
    Cliente realizarSaque(Long clienteId, Double valor);
}
