package br.com.tgid.service;

import br.com.tgid.entity.Transacao;
import br.com.tgid.dtos.request.DepositoRequest;
import br.com.tgid.dtos.request.SaqueRequest;
import org.springframework.stereotype.Service;

@Service
public interface TransacaoService {
    Transacao realizarDeposito(Long clienteId, DepositoRequest depositoRequest);
    Transacao realizarSaque(Long clienteId, SaqueRequest saqueRequest);
}
