package br.com.tgid.service;

import br.com.tgid.entity.Empresa;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmpresaService {
    Empresa criar(Empresa empresa);
    Empresa buscarporCnpj(String cnpj);
    List<Empresa> buscarTodos();
    Empresa atualizar(Empresa empresa);
    void excluir(Long id);
}
