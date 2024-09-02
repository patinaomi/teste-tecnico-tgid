package br.com.tgid.controller;

import br.com.tgid.entity.Transacao;
import br.com.tgid.request.DepositoRequest;
import br.com.tgid.request.SaqueRequest;
import br.com.tgid.response.TransacaoResponse;
import br.com.tgid.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transacoes")
public class TransacaoController {

    private final TransacaoService transacaoService;


    @PostMapping("/deposito")
    public ResponseEntity<TransacaoResponse> realizarDeposito(@RequestParam Long clienteId, @RequestBody DepositoRequest depositoRequest) {
        Transacao transacao = transacaoService.realizarDeposito(clienteId, depositoRequest);
        TransacaoResponse response = TransacaoResponse.fromTransacao(transacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/saque")
    public ResponseEntity<TransacaoResponse> realizarSaque(@RequestParam Long clienteId, @RequestBody SaqueRequest saqueRequest) {
        Transacao transacao = transacaoService.realizarSaque(clienteId, saqueRequest);
        TransacaoResponse response = TransacaoResponse.fromTransacao(transacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
