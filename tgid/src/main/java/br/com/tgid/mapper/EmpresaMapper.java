package br.com.tgid.mapper;

import br.com.tgid.entity.Empresa;
import br.com.tgid.dtos.request.EmpresaRequest;
import br.com.tgid.dtos.response.EmpresaResponse;
import org.springframework.stereotype.Component;

@Component
public class EmpresaMapper {
    public Empresa toEmpresaEntity(EmpresaRequest request) {
        return new Empresa(
                null,
                request.nome(),
                request.cnpj(),
                request.email(),
                request.saldo()
        );
    }

    public EmpresaResponse toEmpresaResponse(Empresa empresa) {
        return new EmpresaResponse(
                empresa.getId(),
                empresa.getNome(),
                empresa.getCnpj(),
                empresa.getEmail(),
                empresa.getSaldo()
        );
    }
}
