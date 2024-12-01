package br.com.tgid.service;

import br.com.tgid.entity.Cliente;
import br.com.tgid.entity.Empresa;
import br.com.tgid.entity.Transacao;
import br.com.tgid.enums.TipoTaxa;
import br.com.tgid.enums.TipoTransacao;
import br.com.tgid.exception.TransacaoException;
import br.com.tgid.repository.ClienteRepository;
import br.com.tgid.repository.EmpresaRepository;
import br.com.tgid.repository.TransacaoRepository;
import br.com.tgid.dtos.request.DepositoRequest;
import br.com.tgid.dtos.request.SaqueRequest;
import br.com.tgid.service.impl.EmailServiceImpl;
import br.com.tgid.service.impl.TransacaoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static br.com.tgid.enums.TipoTransacao.SAQUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransacaoServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private TransacaoRepository transacaoRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private EmailServiceImpl emailService;

    @InjectMocks
    private TransacaoServiceImpl transacaoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void realizarDepositoComSucesso() {
        long clienteId = 1L;

        // Arrange
        DepositoRequest depositoRequest = new DepositoRequest(1L,100.0);

        Cliente cliente = new Cliente(null, "Pati", "Naomi", "667.987.040-26", "pati.nao@gmail.com", 200.0);
        cliente.setId(1L);

        Empresa empresa = new Empresa(null, "Empresa Teste", "48.474.209/0001-13", "empresa@teste.com", 1000.0);
        empresa.setId(1L);

        when(clienteRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(cliente));
        when(empresaRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(empresa));
        when(transacaoRepository.save(any(Transacao.class))).thenAnswer(i -> {
            Transacao transacao = (Transacao) i.getArguments()[0];
            transacao.setId(1L);
            return transacao;
        });

        // Act
        Transacao transacao = transacaoService.realizarDeposito(1L, depositoRequest);

        // Assert
        assertNotNull(transacao);
        assertEquals(TipoTransacao.DEPOSITO, transacao.getTipo());
        assertEquals(100.0, transacao.getValor());
        assertEquals(100.0, cliente.getSaldo());  // Saldo atualizado após o depósito
        assertEquals(1100.0, empresa.getSaldo());  // Saldo atualizado da empresa
        verify(emailService, times(1)).enviarEmail(anyString(), anyString(), anyString());
    }

    @Test
    void realizarSaqueComSucesso() {
        // Arrange
        Long clienteId = 1L;
        Long empresaId = 1L;

        Cliente cliente = new Cliente(clienteId, "Pati", "Naomi", "667.987.040-26", "pati.nao@gmail.com", 100.0);
        Empresa empresa = new Empresa(empresaId, "Empresa Teste", "48.474.209/0001-13", "empresa@teste.com", 200.0);
        SaqueRequest saqueRequest = new SaqueRequest(empresaId, 50.0, TipoTaxa.CAIXA24HORAS);

        double taxa = 50.0 * 0.03;  // Calculando a taxa de 3%
        double valorRecebidoCliente = 50.0 - taxa;  // Valor que o cliente receberá

        // Simulando os métodos do repositório
        when(clienteRepository.findById(clienteId)).thenReturn(Optional.of(cliente));
        when(empresaRepository.findById(empresaId)).thenReturn(Optional.of(empresa));
        when(transacaoRepository.save(any(Transacao.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Transacao transacao = transacaoService.realizarSaque(clienteId, saqueRequest);

        // Assert
        assertEquals(150, empresa.getSaldo()); // Empresa tinha 200 - 50 = 150
        assertEquals(148.5, cliente.getSaldo()); // Cliente tinha 100 + valorRecebidoCliente (48.5) = 148.5
        assertEquals(SAQUE, transacao.getTipo());
        assertEquals(50.0, transacao.getValor());
    }

    @Test
    void realizarDepositoComSaldoInsuficiente() {
        // Arrange
        DepositoRequest depositoRequest = new DepositoRequest(1L, 100.0);  // Empresa ID e valor do depósito

        // Configura um cliente com saldo insuficiente para o teste
        Cliente cliente = new Cliente(null, "Pati", "Naomi", "667.987.040-26", "pati.nao@gmail.com", 50.0); // Saldo menor que o valor do depósito
        cliente.setId(1L);

        Empresa empresa = new Empresa(null, "Empresa Teste", "48.474.209/0001-13", "empresa@teste.com", 1000.0);
        empresa.setId(1L);

        when(clienteRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(cliente));
        when(empresaRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(empresa));

        // Act & Assert
        TransacaoException exception = assertThrows(TransacaoException.class,
                () -> transacaoService.realizarDeposito(1L, depositoRequest));

        assertEquals("Saldo insuficiente do cliente", exception.getMessage());
        verify(emailService, times(1)).enviarEmailErroTransacao(1L, "Saldo insuficiente do cliente");
    }

}