package br.com.tgid.controller;

import br.com.tgid.entity.Empresa;
import br.com.tgid.mapper.EmpresaMapper;
import br.com.tgid.dtos.request.EmpresaRequest;
import br.com.tgid.dtos.response.EmpresaResponse;
import br.com.tgid.service.EmpresaService;
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
@RequestMapping(value = "/empresas", produces = "application/json")
@Tag(name = "empresa", description = "Operações relacionadas a empresas")
public class EmpresaController {

    private final EmpresaService empresaService;
    private final EmpresaMapper empresaMapper;

    @Operation(
            summary = "Cria uma nova empresa",
            description = "Cria uma nova empresa com base nos dados informados",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Dados da empresa a ser criada",
                    required = true,
                    content = @Content(schema = @Schema(implementation = EmpresaRequest.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empresa criada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmpresaResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content)
    })
    @PostMapping("/criar")
    public ResponseEntity<EmpresaResponse> criar(@RequestBody EmpresaRequest empresaRequest) {
        Empresa empresa = empresaMapper.toEmpresaEntity(empresaRequest);
        Empresa empresaSalva = empresaService.criar(empresa);
        EmpresaResponse empresaResponse = empresaMapper.toEmpresaResponse(empresaSalva);
        return ResponseEntity.status(HttpStatus.CREATED).body(empresaResponse);
    }
}
