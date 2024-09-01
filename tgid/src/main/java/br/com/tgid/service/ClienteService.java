package br.com.tgid.service;

import br.com.tgid.entity.Cliente;
import org.springframework.stereotype.Service;

@Service
public interface ClienteService {

    Cliente criar(Cliente cliente);
}
