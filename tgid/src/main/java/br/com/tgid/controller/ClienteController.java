package br.com.tgid.controller;

import br.com.tgid.mapper.ClienteMapper;
import br.com.tgid.request.DepositoRequest;
import br.com.tgid.response.ClienteResponse;
import br.com.tgid.response.DepositoResponse;
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
        Cliente cliente = clienteMapper.toClienteEntity(clienteRequest);
        Cliente clienteSalvo = clienteService.criar(cliente);
        ClienteResponse clienteResponse = clienteMapper.toClienteResponse(clienteSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponse);
    }
}
