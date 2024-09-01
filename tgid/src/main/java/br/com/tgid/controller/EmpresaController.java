package br.com.tgid.controller;

import br.com.tgid.entity.Empresa;
import br.com.tgid.mapper.EmpresaMapper;
import br.com.tgid.request.EmpresaRequest;
import br.com.tgid.response.EmpresaResponse;
import br.com.tgid.service.EmpresaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/empresas")
public class EmpresaController {
    private final EmpresaService empresaService;
    private final EmpresaMapper empresaMapper;

    @PostMapping("/criar")
    public ResponseEntity<EmpresaResponse> criar(@RequestBody EmpresaRequest empresaRequest) {
        Empresa empresa = empresaMapper.toEmpresaEntity(empresaRequest);
        Empresa empresaSalva = empresaService.criar(empresa);
        EmpresaResponse empresaResponse = empresaMapper.toEmpresaResponse(empresaSalva);
        return ResponseEntity.status(HttpStatus.CREATED).body(empresaResponse);
    }
}
