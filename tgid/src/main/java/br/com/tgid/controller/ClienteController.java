package br.com.tgid.controller;

import br.com.tgid.mapper.ClienteMapper;
import br.com.tgid.response.ClienteResponse;
import br.com.tgid.service.ClienteService;
import br.com.tgid.entity.Cliente;
import br.com.tgid.request.ClienteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

    @PostMapping("/criar")
    public ResponseEntity<ClienteResponse> criar(@RequestBody ClienteRequest clienteRequest) {
        Cliente cliente = clienteMapper.toEntity(clienteRequest);
        Cliente clienteSalvo = clienteService.criar(cliente);
        ClienteResponse clienteResponse = clienteMapper.toResponse(clienteSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponse);
    }

    @PostMapping("/depositar/{clienteId}")
    public ResponseEntity<DepositoResponse> depositar(@PathVariable Long clienteId, @RequestBody DepositoRequest depositoRequest) {

        DepositoResponse depositoResponse = clienteService.realizarDeposito(clienteId, depositoRequest.getValor());
        return ResponseEntity.status(HttpStatus.OK).body(depositoResponse);
    }




}
