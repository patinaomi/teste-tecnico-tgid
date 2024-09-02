package br.com.tgid.service;

import br.com.tgid.entity.Cliente;
import br.com.tgid.repository.ClienteRepository;
import br.com.tgid.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarClienteComSucesso() {
        // Dados de entrada
        Cliente cliente = new Cliente(null, "Pati", "Naomi", "667.987.040-26", "pati.nao@gmail.com", 100.0);

        // Configuração do Mock
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // Execução do método de teste
        Cliente clienteCriado = clienteService.criar(cliente);

        // Verificação
        assertEquals("Pati", clienteCriado.getNome());
        assertEquals("Naomi", clienteCriado.getSobrenome());
        assertEquals("667.987.040-26", clienteCriado.getCpf());
    }
}

