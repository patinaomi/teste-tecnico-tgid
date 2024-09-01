package br.com.tgid.service.impl;

import br.com.tgid.service.EmpresaService;
import br.com.tgid.entity.Empresa;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmpresaServiceImpl implements EmpresaService {
    @Override
    public Empresa criar(Empresa empresa) {
        return null;
    }

    @Override
    public Empresa buscarporCnpj(String cnpj) {
        return null;
    }

    @Override
    public List<Empresa> buscarTodos() {
        return List.of();
    }

    @Override
    public Empresa atualizar(Empresa empresa) {
        return null;
    }

    @Override
    public void excluir(Long id) {

    }
}
