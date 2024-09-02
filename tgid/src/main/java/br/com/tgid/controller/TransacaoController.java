package br.com.tgid.controller;

import br.com.tgid.entity.Transacao;
import br.com.tgid.request.DepositoRequest;
import br.com.tgid.request.SaqueRequest;
import br.com.tgid.response.TransacaoResponse;
import br.com.tgid.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/transacoes", produces = "application/json")
@Tag(name = "transacao", description = "Operações relacionadas a transações financeiras")
public class TransacaoController {

    private final TransacaoService transacaoService;

    @Operation(
            summary = "Realiza um depósito",
            description = "Realiza um depósito para uma empresa especificada",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do depósito a ser realizado",
                    required = true,
                    content = @Content(schema = @Schema(implementation = DepositoRequest.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Depósito realizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransacaoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PostMapping("/deposito")
    public ResponseEntity<TransacaoResponse> realizarDeposito(@RequestParam Long clienteId, @RequestBody DepositoRequest depositoRequest) {
        Transacao transacao = transacaoService.realizarDeposito(clienteId, depositoRequest);
        TransacaoResponse response = TransacaoResponse.fromTransacao(transacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Realiza um saque",
            description = "Realiza um saque de uma empresa especificada",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados do saque a ser realizado",
                    required = true,
                    content = @Content(schema = @Schema(implementation = SaqueRequest.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Saque realizado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransacaoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PostMapping("/saque")
    public ResponseEntity<TransacaoResponse> realizarSaque(@RequestParam Long clienteId, @RequestBody SaqueRequest saqueRequest) {
        Transacao transacao = transacaoService.realizarSaque(clienteId, saqueRequest);
        TransacaoResponse response = TransacaoResponse.fromTransacao(transacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
