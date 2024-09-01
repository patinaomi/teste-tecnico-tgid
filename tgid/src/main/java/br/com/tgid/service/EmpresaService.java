package br.com.tgid.service;

import br.com.tgid.entity.Empresa;
import org.springframework.stereotype.Service;

@Service
public interface EmpresaService {
    Empresa criar(Empresa empresa);
}
