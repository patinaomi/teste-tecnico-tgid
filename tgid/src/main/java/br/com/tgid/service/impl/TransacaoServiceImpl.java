package br.com.tgid.service.impl;

import br.com.tgid.entity.Cliente;
import br.com.tgid.entity.Empresa;
import br.com.tgid.entity.Transacao;
import br.com.tgid.enums.TipoTaxa;
import br.com.tgid.enums.TipoTransacao;
import br.com.tgid.repository.ClienteRepository;
import br.com.tgid.repository.EmpresaRepository;
import br.com.tgid.repository.TransacaoRepository;
import br.com.tgid.request.DepositoRequest;
import br.com.tgid.request.SaqueRequest;
import br.com.tgid.service.TransacaoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TransacaoServiceImpl implements TransacaoService {

    private final ClienteRepository clienteRepository;
    private final EmpresaRepository empresaRepository;
    private final TransacaoRepository transacaoRepository;

    @Override
    public Transacao realizarDeposito(Long clienteId, DepositoRequest depositoRequest) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Empresa empresa = empresaRepository.findById(depositoRequest.empresaId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        // Verifica se o cliente tem saldo suficiente para o depósito
        if (cliente.getSaldo() < depositoRequest.valor()) {
            throw new RuntimeException("Saldo insuficiente do cliente");
        }

        // Atualização de valores
        cliente.setSaldo(cliente.getSaldo() - depositoRequest.valor());
        empresa.setSaldo(empresa.getSaldo() + depositoRequest.valor());

        // Salvando dados atualizados
        clienteRepository.save(cliente);
        empresaRepository.save(empresa);

        // Cria e salva uma nova transação de depósito
        Transacao transacao = new Transacao();
        transacao.setCliente(cliente);
        transacao.setEmpresa(empresa);
        transacao.setTipo(TipoTransacao.DEPOSITO);
        transacao.setValor(depositoRequest.valor());
        transacao.setData(LocalDateTime.now());

        transacaoRepository.save(transacao);

        return transacao;
    }

    @Override
    public Transacao realizarSaque(Long clienteId, SaqueRequest saqueRequest) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        Empresa empresa = empresaRepository.findById(saqueRequest.empresaId())
                .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));

        // Calcula o valor da taxa baseado no tipo de taxa e no valor do saque
        double taxa = calcularTaxa(saqueRequest.tipoTaxa(), saqueRequest.valor());

        // Calcula o valor que o cliente receberá após a dedução da taxa
        double valorRecebidoCliente = saqueRequest.valor() - taxa;

        // Verifica se a empresa tem saldo suficiente para cobrir o saque
        if (empresa.getSaldo() < saqueRequest.valor()) {
            throw new RuntimeException("Saldo insuficiente da empresa para cobrir o saque.");
        }

        // Verifica se o cliente tem saldo suficiente para cobrir a taxa
        if (valorRecebidoCliente < 0) {
            throw new RuntimeException("Saldo insuficiente do cliente para cobrir a taxa.");
        }

        // Atualização de valores
        cliente.setSaldo(cliente.getSaldo() + valorRecebidoCliente);
        empresa.setSaldo(empresa.getSaldo() - saqueRequest.valor());

        // Salvando dados atualizados
        clienteRepository.save(cliente);
        empresaRepository.save(empresa);

        // Cria e salva uma nova transação de saque
        Transacao transacao = new Transacao();
        transacao.setCliente(cliente);
        transacao.setEmpresa(empresa);
        transacao.setTipo(TipoTransacao.SAQUE);
        transacao.setValor(saqueRequest.valor());
        transacao.setData(LocalDateTime.now());

        transacaoRepository.save(transacao);

        return transacao;
    }

    private double calcularTaxa(TipoTaxa tipoTaxa, double valor) {
        return switch (tipoTaxa) {
            case BANCO -> 0.0;  // Sem taxa para saques no banco
            case CAIXA24HORAS -> valor * 0.03;  // 3% de taxa para saques em caixas 24 horas
            case LOTERICA -> valor * 0.02;  // 2% de taxa para saques em lotéricas
            default -> throw new IllegalArgumentException("Tipo de taxa desconhecido: " + tipoTaxa);
        };
    }
}
