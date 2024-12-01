package br.com.tgid.controller;

import br.com.tgid.mapper.ClienteMapper;
import br.com.tgid.dtos.response.ClienteResponse;
import br.com.tgid.service.ClienteService;
import br.com.tgid.entity.Cliente;
import br.com.tgid.dtos.request.ClienteRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/clientes", produces = "application/json")
@Tag(name = "cliente", description = "Operações relacionadas a clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final ClienteMapper clienteMapper;

    @Operation(
            summary = "Cria um novo cliente",
            description = "Cria um novo cliente com base nos dados informados"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PostMapping("/criar")
    public ResponseEntity<ClienteResponse> criar(@RequestBody ClienteRequest clienteRequest) {
        Cliente cliente = clienteMapper.toClienteEntity(clienteRequest);
        Cliente clienteSalvo = clienteService.criar(cliente);
        ClienteResponse clienteResponse = clienteMapper.toClienteResponse(clienteSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponse);
    }
}
