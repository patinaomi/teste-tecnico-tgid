package br.com.tgid.service.impl;

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
import br.com.tgid.service.TransacaoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@AllArgsConstructor
@Service
public class TransacaoServiceImpl implements TransacaoService {

    private final ClienteRepository clienteRepository;
    private final EmpresaRepository empresaRepository;
    private final TransacaoRepository transacaoRepository;
    private final RestTemplate restTemplate;
    private final EmailServiceImpl emailService;

    private final String webhookUrl = "https://webhook.site/3366311f-36a0-426e-bcde-3cb7e93dc668";

    @Override
    public Transacao realizarDeposito(Long clienteId, DepositoRequest depositoRequest) {
        try {
            Cliente cliente = clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new TransacaoException("Cliente não encontrado"));

            Empresa empresa = empresaRepository.findById(depositoRequest.empresaId())
                    .orElseThrow(() -> new TransacaoException("Empresa não encontrada"));

            // Verifica se o cliente tem saldo suficiente para o depósito
            if (cliente.getSaldo() < depositoRequest.valor()) {
                throw new TransacaoException("Saldo insuficiente do cliente");
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
            enviarCallback(transacao);

            String mensagemEmailDeposito = String.format(
                    "Olá, %s! Seu depósito foi realizado com sucesso!\n\n" +
                            "Detalhes da Transação:\nData: %s\nValor: R$%.2f\n" +
                            "Saldo Atualizado: R$%.2f\n\nObrigado por usar nossos serviços!",
                    cliente.getNome(),
                    transacao.getData(),
                    depositoRequest.valor(),
                    cliente.getSaldo()
            );

            emailService.enviarEmail(cliente.getEmail(), "Depósito Realizado", mensagemEmailDeposito);
            return transacao;

        } catch (TransacaoException e) {
            emailService.enviarEmailErroTransacao(clienteId, e.getMessage());
            throw e;
        }
    }


    @Override
    public Transacao realizarSaque(Long clienteId, SaqueRequest saqueRequest) {
        try {
            Cliente cliente = clienteRepository.findById(clienteId)
                    .orElseThrow(() -> new TransacaoException("Cliente não encontrado"));

            Empresa empresa = empresaRepository.findById(saqueRequest.empresaId())
                    .orElseThrow(() -> new TransacaoException("Empresa não encontrada"));

            // Calcula o valor da taxa baseado no tipo de taxa e no valor do saque
            double taxa = calcularTaxa(saqueRequest.tipoTaxa(), saqueRequest.valor());

            // Calcula o valor que o cliente receberá após a dedução da taxa
            double valorRecebidoCliente = saqueRequest.valor() - taxa;

            // Verifica se a empresa tem saldo suficiente para cobrir o saque
            if (empresa.getSaldo() < saqueRequest.valor()) {
                throw new TransacaoException("Saldo insuficiente da empresa para cobrir o saque.");
            }

            // Verifica se o cliente tem saldo suficiente para cobrir a taxa
            if (valorRecebidoCliente < 0) {
                throw new TransacaoException("Saldo insuficiente do cliente para cobrir a taxa.");
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
            enviarCallback(transacao);

            String mensagemEmailSaque = String.format(
                    "Olá, %s! Seu saque foi realizado com sucesso!\n\nDetalhes da Transação:\nData: %s\nValor: R$%.2f\nTaxa Aplicada: R$%.2f\nSaldo Atualizado: R$%.2f\n\nObrigado por usar nossos serviços!",
                    cliente.getNome(),
                    transacao.getData(),
                    saqueRequest.valor(),
                    taxa,
                    cliente.getSaldo()
            );

            emailService.enviarEmail(cliente.getEmail(), "Saque Realizado", mensagemEmailSaque);

            return transacao;
        } catch (TransacaoException e) {
            emailService.enviarEmailErroTransacao(clienteId, e.getMessage());
            throw e;
        }
    }


    private double calcularTaxa(TipoTaxa tipoTaxa, double valor) {
        return switch (tipoTaxa) {
            case BANCO -> 0.0;  // Sem taxa para saques no banco
            case CAIXA24HORAS -> valor * 0.03;  // 3% de taxa para saques em caixas 24 horas
            case LOTERICA -> valor * 0.02;  // 2% de taxa para saques em lotéricas
            default -> throw new IllegalArgumentException("Tipo de taxa desconhecido: " + tipoTaxa);
        };
    }

    private void enviarCallback(Transacao transacao) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("idTransacao", transacao.getId());
            payload.put("clienteId", transacao.getCliente().getId());
            payload.put("empresaId", transacao.getEmpresa().getId());
            payload.put("tipoTransacao", transacao.getTipo());
            payload.put("valor", transacao.getValor());
            payload.put("data", transacao.getData().toString());

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");

            ResponseEntity<String> response = restTemplate.postForEntity(webhookUrl, payload, String.class);
            System.out.println("Callback enviado com sucesso: " + response.getBody());
        } catch (Exception e) {
            System.out.println("Erro ao enviar callback: " + e.getMessage());
        }
    }
}
